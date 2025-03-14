package menus;

import chess.ChessGame;
import consoleRepl.State;
import model.GameData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class PostLogin {
    private static String username = null;
    public static State state = State.LOGGED_IN;

    public PostLogin() {
    }

    public static String eval(String input) {
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
            var username = params[0];
            var password = params[1];
            var email = params[2];
            // send it over to the server
            return String.format("You registered as %s and are now signed in\n", username);
        }
        throw new Exception("Expected: <username> <password> <email>\n");
    }

    public static String observe(String... params) throws Exception {
        if (params.length >= 2) {

            var username = params[0];
            var password = params[1];
            var email = params[2];
            // send it over to the server
            return String.format("You registered as %s and are now signed in\n", username);
        }
        throw new Exception("Expected: <username> <password> <email>\n");
    }

    public static String create(String... params) throws Exception {
        if (params.length >= 2) {

            var username = params[0];
            var password = params[1];
            var email = params[2];
            // send it over to the server
            return String.format("You registered as %s and are now signed in\n", username);
        }
        throw new Exception("Expected: <username> <password> <email>\n");
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
