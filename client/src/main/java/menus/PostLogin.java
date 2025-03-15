package menus;

import chess.ChessGame;
import client.ServerFacade;
import consoleRepl.GameState;
import consoleRepl.State;
import model.GameData;
import ui.DrawChessBoard;

import java.util.*;

public class PostLogin {
    public static State state = State.LOGGED_IN;
    public static GameState joined = GameState.NOT_JOINED;
    static HashMap<Integer, String> gamesAndTmpIDs = new HashMap<>();

    public PostLogin() {
    }

    public static String eval(String input, ServerFacade server) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "create" -> create(params);
                case "list" -> list();
                case "join" -> join(params);
                case "observe" -> observe(params);
                case "logout" -> logout();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public static String logout() {
        state = State.LOGGED_OUT;
        PreLogin.state = State.LOGGED_OUT;
        return "You have now logged out.\n";
    }

    public static String list() {
        // get an array of games
        Collection<GameData> games = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        int index = 1;
        for (GameData game: games) {
            gamesAndTmpIDs.put(index, game.gameName());
            result.append(index).append(". ");
            result.append(game.gameName()).append("\n\tWhite player: ");
            if (game.whiteUsername() != null){
                result.append(game.whiteUsername());
            } else {
                result.append("Available");
            }
            result.append("\n\tBlack player: ");
            if (game.blackUsername() != null){
                result.append(game.blackUsername());
            } else {
                result.append("Available");
            }
            result.append("\n");
            index += 1;
        }
        return result.toString();
    }

    public static String join(String... params) throws Exception {
        if (params.length >= 2) {
            var id = params[0];
            var player = params[1];
            if ((player.equals("WHITE")) || (player.equals("BLACK"))) {
                ChessGame chess = new ChessGame();
                joined = GameState.JOINED_GAME;
                GameMenu.joined = GameState.JOINED_GAME;
                // try to join game and catch errors otherwise join and show the board
                DrawChessBoard d = new DrawChessBoard();
                d.draw(chess, player);
                return help();
            }
        }
        throw new Exception("Expected: <id> <WHITE|BLACK>\n");
    }

    public static String observe(String... params) throws Exception {
        if (params.length >= 1) {
            var id = params[0];
            String gameName = gamesAndTmpIDs.get(id);
            // get the requested chess game and display it
            ChessGame chess = new ChessGame();
            DrawChessBoard d = new DrawChessBoard();
            d.draw(chess, "white");
            return "";
        }
        throw new Exception("Expected: <id>\n");
    }

    public static String create(String... params) throws Exception {
        if (params.length >= 1) {
            var name = params[0];
            // create the game
            return String.format("Your game, %s, has been created\n", name);
        }
        throw new Exception("Expected: <game name>\n");
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
