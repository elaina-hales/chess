package menus;

import chess.ChessGame;
import client.ReturnGamesObject;
import client.ReturnObject;
import client.ServerFacade;
import repl.GameState;
import repl.State;
import model.GameData;
import requestsresults.GamesResult;
import ui.DrawChessBoard;

import java.util.*;

public class PostLogin {
    public static State state = State.LOGGED_IN;
    public static GameState joined = GameState.NOT_JOINED;
    private static HashMap<Integer, Integer> gameIDsAndTmpIDs = new HashMap<>();

    public PostLogin() {
    }

    public static String eval(String input, ServerFacade server, String authToken) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "create" -> create(authToken, server, params);
                case "list" -> list(server, authToken);
                case "join" -> join(server, authToken, params);
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

    public static String join(ServerFacade server, String authToken, String... params) {
        if (params.length >= 2) {
            try {
                int id = Integer.parseInt(params[0]);
                var player = params[1];
                int statusCode;
                ReturnObject r = null;
                if (gameIDsAndTmpIDs.isEmpty()){
                    statusCode = 800;
                } else {
                    r = server.join(authToken, gameIDsAndTmpIDs.get(id), player);
                    statusCode = r.statusCode();
                }
                return switch (statusCode) {
                    case 200 -> successJoin(player);
                    case 400 -> "Error: bad request. Expected: <id> <WHITE|BLACK>. Please try again.\n";
                    case 401 -> "You are unauthorized to do this. Please try again.\n";
                    case 403 -> "That color is already taken. Select a different color or game and try again.\n";
                    case 800 -> "You must list available games before joining one. Enter 'list' to list available games.\n";
                    default -> "Server Error: " + r.statusMessage() + "\n";
                };
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "Error: bad request. Expected: <id> <WHITE|BLACK>. Please try again.\n";
        }
    }

    public static String successJoin(String player){
        joined = GameState.JOINED_GAME;
        GameMenu.joined = GameState.JOINED_GAME;
        DrawChessBoard d = new DrawChessBoard();
        d.draw(new ChessGame(), player);
        return GameMenu.help();
    }

    public static String observe(String... params) throws Exception {
        if (params.length >= 1) {
            var id = params[0];
            int gameID = gameIDsAndTmpIDs.get(id);
            ChessGame chess = new ChessGame();
            DrawChessBoard d = new DrawChessBoard();
            d.draw(chess, "white");
            return "";
        }
        throw new Exception("Expected: <id>\n");
    }

    public static String create(String authToken, ServerFacade server, String... params) {
        try {
            ReturnObject r = server.create(authToken, params[0]);
            return switch (r.statusCode()) {
                case 200 -> String.format("Your game, %s, has been created.\n", params[0]);
                case 400 -> "Error: bad input. Expected: <gameName>. Try again.\n";
                case 401 -> "You are unauthorized to do this. Please try again.\n";
                default -> "Server Error: " + r.statusMessage() + "\n";
            };
        } catch (Exception e) {
            return e.getMessage();
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
