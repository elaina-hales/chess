package menus;

import consoleRepl.State;

import java.util.Arrays;

public class PostLogin {
    private static String username = null;
    public static State state = State.SIGNEDOUT;

    public PostLogin() {
    }

    public static String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login" -> signIn(params);
                case "create" -> register(params);
                case "list" -> register(params);
                case "play" -> register(params);
                case "observe" -> register(params);
                case "logout" -> register(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public static String signIn(String... params) throws Exception {
        if (params.length >= 1) {
            state = State.SIGNEDIN;
            username = String.join("-", params);
            return String.format("Success! You signed in as %s.", username);
        }
        throw new Exception("Expected: <username> <password>");
    }

    public static String register(String... params) throws Exception {
        if (params.length >= 2) {
            state = State.SIGNEDIN;
            var username = params[0];
            var password = params[1];
            var email = params[2];
            // send it over to the server
            return String.format("You registered as %s and are now signed in", username);
        }
        throw new Exception("Expected: <username> <password> <email>");
    }

    public static String help() {
        return """
                    login <username> <password>
                    register <username> <password> <email>
                    help
                    quit
                """;
    }
}
