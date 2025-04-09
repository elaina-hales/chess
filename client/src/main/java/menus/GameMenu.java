package menus;

import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import client.ServerFacade;
import repl.GameState;
import ui.DrawChessBoard;
import websocket.ServerMessageObserver;
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
    private String serverUrl;
    private static boolean isHighlight = false;
    private static ChessPosition startPosition = new ChessPosition(-1, -1);


    public GameMenu(String serverUrl) {
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
    }

    public String eval(String input, String givenUsername) {
        try {
            username = givenUsername;
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "redraw" -> redraw();
                case "leave" -> leave();
                case "move" -> makeMove(params);
                case "resign" -> resign();
                case "highlight" -> highlight(params[0]);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public static String resign(){
        if (PostLogin.isObserver){
            return "You cannot resign as an observer.\n";
        } else {
            ChessGame chess = new ChessGame();
            chess.setIsOver(true);
            joined = GameState.NOT_JOINED;
            PostLogin.joined = GameState.NOT_JOINED;
            return "You have successfully resigned.\n";
        }
    }

    public static String leave() {
        joined = GameState.NOT_JOINED;
        PostLogin.joined = GameState.NOT_JOINED;
        PostLogin.isObserver = false;
        return "You have successfully left the game.\n";
    }

    public static String redraw() {
        DrawChessBoard d = new DrawChessBoard();
        String strColor;
        if ((color == ChessGame.TeamColor.WHITE) || (PostLogin.isObserver)) {
            strColor = "white";
        } else {
            strColor = "black";
        }
        d.draw(new ChessGame(), strColor, false);
        return "";
    }


    public static String highlight(String input) {
        if (!Pattern.matches("[a-h][1-8]", input)) {
            return "Invalid input. Input must be a lowercase letter (a-h) followed by a number (1-8).\n";
        }

        Character e = input.charAt(0);
        int row = Character.getNumericValue(input.charAt(1));
        int col = colMap.get(e);
        startPosition = new ChessPosition(row, col);
        ChessGame chess = new ChessGame();
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
        d.highlight(new ChessGame(), strColor, startPosition);
        return "";
    }

    public static String makeMove(String... params) {
        if (PostLogin.isObserver){
            return "You are not authorized to make moves.\n";
        }
        if (params.length != 2 || !Pattern.matches("[a-h][1-8]", params[0]) || !Pattern.matches("[a-h][1-8]", params[1])) {
            return "Invalid input. Input must be two positions (a lowercase letter (a-h) followed by a number (1-8)).\n";
        }
        ChessGame chess = new ChessGame();

        Character e = params[0].charAt(0);
        int startRow = Character.getNumericValue(params[0].charAt(1));
        int startCol = colMap.get(e);
        ChessPosition startPos = new ChessPosition(startRow, startCol);

        Character e2 = params[1].charAt(0);
        int endRow = Character.getNumericValue(params[1].charAt(1));
        int endCol = colMap.get(e2);
        ChessPosition endPos = new ChessPosition(endRow, endCol);
        if (chess.getBoard().getPiece(startPos) == null) {
            return "There is no piece at your start position of" + params[0] + ".\n";
        } else if (chess.getBoard().getPiece(startPos).getTeamColor() != color) {
            return "The piece at your start position of" + params[0] + "is not your same team color.\n";
        }
        ChessPiece piece = chess.getBoard().getPiece(startPos);
//        if (piece.getPieceType().equals(ChessPiece.PieceType.PAWN) && (endRow == 8)) {
//            Scanner scanner = new Scanner(System.in);
//            String i = scanner.next();
//
//        }

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
                    move <start> <end> - make a move
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
        DrawChessBoard d = new DrawChessBoard();
        String strColor;
        if (color == BLACK){
            strColor = "black";
        } else  {
            strColor = "white";
        }
        if (isHighlight){
            d.highlight(game, strColor, startPosition);
        } else {
            d.draw(game, strColor, false);
        }
    }
}
