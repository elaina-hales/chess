package menus;

import chess.ChessGame;
import client.*;
import exception.ResponseException;
import model.GameData;
import repl.GameState;
import repl.State;
import ui.DrawChessBoard;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

public class PostLogin {
    public static State state = State.LOGGED_IN;
    public static GameState joined = GameState.NOT_JOINED;
    private static final HashMap<Integer, Integer> gameIDsAndTmpIDs = new HashMap<>();
    public static boolean isObserver = false;

    public PostLogin() {
    }

    public static String eval(String input, ServerFacade server, String authToken, WebSocketFacade ws, String username) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "create" -> create(authToken, server, params);
                case "list" -> list(server, authToken);
                case "join" -> join(username, ws, server, authToken, params);
                case "observe" -> observe(params);
                case "logout" -> logout(server, authToken);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public static String logout(ServerFacade server, String authToken) {
        try {
            ReturnObject r = server.logout(authToken);
            if (r.statusCode() == 200){
                state = State.LOGGED_OUT;
                PreLogin.state = State.LOGGED_OUT;
            }
            return switch (r.statusCode()) {
                case 200 -> "You have now logged out.\n";
                case 401 -> "Log in unsuccessful. Please try again.\n";
                default -> "Server Error: " + r.statusMessage() + "\n";
            };
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private static String listHelper(GamesResult body){
        StringBuilder result = new StringBuilder();
        int index = 1;
        for (GameData game: body.games()) {
            gameIDsAndTmpIDs.put(index, game.gameID());
            result.append(index).append(". ");
            result.append(game.gameName()).append("\n\tWhite player: ");
            if (game.whiteUsername() != null) {
                result.append(game.whiteUsername());
            } else {
                result.append("Available");
            }
            result.append("\n\tBlack player: ");
            if (game.blackUsername() != null) {
                result.append(game.blackUsername());
            } else {
                result.append("Available");
            }
            result.append("\n");
            index += 1;
        }
        if (result.isEmpty()){
            result.append("No games have been created.\n");
        }
        return result.toString();
    }

    public static String list(ServerFacade server, String authToken) {
        try {
            ReturnGamesObject r = server.listGames(authToken);
            return switch (r.statusCode()) {
                case 200 -> listHelper(r.body());
                case 401 -> "You are unauthorized to do this. Please try again.\n";
                default -> "Server Error: " + r.statusMessage() + "\n";
            };
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static String join(String username, WebSocketFacade ws, ServerFacade server, String authToken, String... params) {
        if (params.length >= 2) {
            try {
                var player = params[1];
                int statusCode;
                ReturnObject r = null;
                int id = 0;
                if (Pattern.matches("[a-zA-Za-z]*", params[0])){
                    statusCode = 400;
                } else {
                    id = Integer.parseInt(params[0]);
                    if (gameIDsAndTmpIDs.isEmpty()){
                        statusCode = 800;
                    } else if (id > gameIDsAndTmpIDs.size()) {
                        statusCode = 900;
                    } else {
                        r = server.join(authToken, gameIDsAndTmpIDs.get(id), player);
                        statusCode = r.statusCode();
                    }
                }
                return switch (statusCode) {
                    case 200 -> successJoin(player, ws, gameIDsAndTmpIDs.get(id), username);
                    case 400 -> "Error: bad input. Expected: <id> <WHITE|BLACK>. Please try again.\n";
                    case 401 -> "You are unauthorized to do this. Please try again.\n";
                    case 403 -> "That color is already taken. Select a different color or game and try again.\n";
                    case 800 -> "You must list available games before joining one. Enter 'list' to list available games.\n";
                    case 900 -> "That index is not in this list. Try again.\n";
                    default -> "Server Error: " + r.statusMessage() + "\n";
                };
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "Error: bad request. Expected: <id> <WHITE|BLACK>. Please try again.\n";
        }
    }

    public static String successJoin(String player, WebSocketFacade ws, int gameID, String username) throws ResponseException {
        joined = GameState.JOINED_GAME;
        GameMenu.joined = GameState.JOINED_GAME;
        ChessGame.TeamColor current;
        if (Objects.equals(player, "black")) {
            current = ChessGame.TeamColor.BLACK;
        } else {
            current = ChessGame.TeamColor.WHITE;
        }
        ws.sendWsJoin(username, gameID);
        GameMenu.color = current;
        DrawChessBoard d = new DrawChessBoard();
        d.draw(new ChessGame(), player, false);
        return GameMenu.help();
    }

    public static String observe(String... params) throws Exception {
        if (params.length >= 1) {
            var id = params[0];
            ChessGame chess = new ChessGame();
            DrawChessBoard d = new DrawChessBoard();
            d.draw(chess, "white", false);
            isObserver = true;
            joined = GameState.JOINED_GAME;
            GameMenu.joined = GameState.JOINED_GAME;
            return GameMenu.help();
        }
        throw new Exception("Error: bad input. Expected: <id>\n");
    }

    public static String create(String authToken, ServerFacade server, String... params) {
        if (params.length >= 1) {
            try {
                ReturnCreateObject r = server.create(authToken, params[0]);
                return switch (r.statusCode()) {
                    case 200 -> String.format("Your game, %s, has been created.\n", params[0]);
                    case 400 -> "Error: bad input. Expected: <gameName>. Try again.\n";
                    case 401 -> "You are unauthorized to do this. Please try again.\n";
                    default -> "Server Error: " + r.statusMessage() + "\n";
                };
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "Error: bad input. Expected: <gameName>. Try again.\n";
        }
    }

    public static String help() {
        return """
                    create <name> - a game
                    list - games
                    join <id> <WHITE|BLACK> - a game
                    observe <id> - a game
                    logout - when you are done
                    quit - exit chess
                    help - possible commands
                """;
    }
}
