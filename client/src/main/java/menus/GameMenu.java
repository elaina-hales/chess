package menus;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import client.ServerFacade;
import exception.ResponseException;
import repl.GameState;
import ui.DrawChessBoard;
import websocket.ServerMessageObserver;
import websocket.WebSocketCommunicator;
import websocket.messages.ServerMessage;

import static chess.ChessGame.TeamColor.BLACK;
import static ui.EscapeSequences.*;

import java.util.*;
import java.util.regex.Pattern;

public class GameMenu implements ServerMessageObserver {
    public static GameState joined = GameState.JOINED_GAME;
    public static String username = "";
    public static ChessGame.TeamColor color;
    private static Map<Character, Integer> colMap = new HashMap<>();
    private static ServerFacade server;
    private static String serverUrl;
    private static boolean isHighlight = false;
    private static ChessPosition startPosition = new ChessPosition(-1, -1);
    private static ChessGame chess;
    private static final HashMap<String, ChessPiece.PieceType> PIECE_MAP = new HashMap<>();
    private WebSocketCommunicator ws;

    public GameMenu(String serverUrl, WebSocketCommunicator cm) throws ResponseException {
        ws = cm;
        ws.setObserver(this);
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        colMap.put('a', 1);
        colMap.put('b', 2);
        colMap.put('c', 3);
        colMap.put('d', 4);
        colMap.put('e', 5);
        colMap.put('f', 6);
        colMap.put('g', 7);
        colMap.put('h', 8);

        PIECE_MAP.put("Q", ChessPiece.PieceType.QUEEN);
        PIECE_MAP.put("B", ChessPiece.PieceType.BISHOP);
        PIECE_MAP.put("R", ChessPiece.PieceType.ROOK);
        PIECE_MAP.put("K", ChessPiece.PieceType.KNIGHT);
    }

    public String eval(String input, String givenUsername, String authtoken, int gameID) {
        try {
            username = givenUsername;
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "redraw" -> redraw();
                case "leave" -> leave(authtoken, gameID);
                case "move" -> makeMove(authtoken, gameID, params);
                case "resign" -> resign(authtoken, gameID);
                case "highlight" -> highlight(params[0]);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String resign(String authtoken, int gameID) throws ResponseException {
        ws.sendWsResign(authtoken, gameID);
        if (!PostLogin.isObserver){
            chess.setIsOver(true);
            waitForNotifications();
            return "You have successfully resigned.\n";
        }
        return "";
    }

    public String leave(String authtoken,  int gameID) throws ResponseException {
        ws.sendWsLeave(authtoken, gameID);
        joined = GameState.NOT_JOINED;
        PostLogin.joined = GameState.NOT_JOINED;
        PostLogin.isObserver = false;
        color = null;
        waitForNotifications();
        return "You have successfully left the game.\n";
    }

    public String redraw() {
        DrawChessBoard d = new DrawChessBoard();
        String strColor;
        if ((color == ChessGame.TeamColor.WHITE) || (PostLogin.isObserver)) {
            strColor = "white";
        } else {
            strColor = "black";
        }
        d.draw(chess, strColor, false);
        return "";
    }

    public String highlight(String input) {
        if (!Pattern.matches("[a-h][1-8]", input)) {
            return "Invalid input. Input must be a lowercase letter (a-h) followed by a number (1-8).\n";
        }
        Character e = input.charAt(0);
        int row = Character.getNumericValue(input.charAt(1));
        int col = colMap.get(e);
        startPosition = new ChessPosition(row, col);
        if (chess.getBoard().getPiece(startPosition) == null){
            return "There is no piece at " + input + ".\n";
        }
        DrawChessBoard d = new DrawChessBoard();
        String strColor;
        if ((color == ChessGame.TeamColor.WHITE) || (PostLogin.isObserver)) {
            strColor = "white";
        } else {
            strColor = "black";
        }
        isHighlight = true;
        d.highlight(chess, strColor, startPosition);
        return "";
    }

    public String makeMove(String authtoken,  int gameID, String... params) throws ResponseException {
        if (PostLogin.isObserver){
            return "You are not authorized to make moves.\n";
        }
        if (params.length > 3 || !Pattern.matches("[a-h][1-8]", params[0]) || !Pattern.matches("[a-h][1-8]", params[1])) {
            return "Invalid input. Input must be two positions (a lowercase letter (a-h) followed by a number (1-8)).\n";
        }
        Character e = params[0].charAt(0);
        int startRow = Character.getNumericValue(params[0].charAt(1));
        int startCol = colMap.get(e);
        ChessPosition startPos = new ChessPosition(startRow, startCol);

        Character e2 = params[1].charAt(0);
        int endRow = Character.getNumericValue(params[1].charAt(1));
        int endCol = colMap.get(e2);
        ChessPosition endPos = new ChessPosition(endRow, endCol);

        ChessMove move = new ChessMove(startPos, endPos, null);

        if (chess.getBoard().getPiece(startPos) == null) {
            return "There is no piece at your start position.\n";
        } else if (!chess.getBoard().getPiece(startPos).getTeamColor().equals(color)) {
            return "Invalid move. The piece at your start position is not on your team.\n";
        } if (!chess.validMoves(startPos).contains(move)) {
            return "Invalid move. You may not move there.\n";
        }

        if (chess.getBoard().getPiece(startPos).equals(ChessPiece.PieceType.PAWN) && endRow == 8) {
            String piece = params[2];
            ChessPiece.PieceType pieceType = PIECE_MAP.get(piece);
            if (pieceType == null) {
                return "Promotion Piece Type not found. Try again \n";
            }
            move = new ChessMove(startPos, endPos, pieceType);
        } else {
            move = new ChessMove(startPos, endPos, null);
        }
        ws.sendWsMakeMove(authtoken, gameID, move);
        waitForNotifications();
        return "";
    }

    public static String help() {
        if (PostLogin.isObserver){
            return """
                    redraw - board
                    leave - the game
                    highlight <position> - highlight possible moves for a piece given its start position
                    help - possible commands
                    quit - exit chess
                """;
        }
        return """
                    redraw - board
                    leave - the game
                    move <start> <end> <promotion piece (optional)> -
                        If you are promoting your pawn, include the first letter of the piece you are promoting to.
                        Q = Queen, B = Bishop, R = Rook, K = Knight
                    resign - forfeit and leave the game
                    highlight <position> - highlight possible moves for a piece given its start position
                    help - possible commands
                    quit - exit chess
                """;
    }

    @Override
    public void notify(ServerMessage notification) {
        switch (notification.getServerMessageType()) {
            case NOTIFICATION -> displayNotification(notification.getMessage());
            case ERROR -> displayError(notification.getErrorMessage());
            case LOAD_GAME -> loadGame(notification.getGame());
        }
    }

    public void displayNotification(String message) {
        System.out.println(SET_TEXT_COLOR_BLUE + message);
        System.out.print(RESET_TEXT_COLOR);
        System.out.print("[LOGGED_IN] >>> ");
    }

    public void displayError(String message) {
        System.out.println(SET_TEXT_COLOR_BLUE + message);
        System.out.print(RESET_TEXT_COLOR);
        System.out.print("[LOGGED_IN] >>> ");
    }

    public void loadGame(ChessGame game){
        chess = game;
        DrawChessBoard d = new DrawChessBoard();
        String strColor;
        if (color == BLACK){
            strColor = "black";
        } else {
            strColor = "white";
        }
        d.draw(chess, strColor, false);
    }

    public static void waitForNotifications() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
