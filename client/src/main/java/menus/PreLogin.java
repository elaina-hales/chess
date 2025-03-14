package menus;

import consoleRepl.State;

import java.util.Arrays;

public class PreLogin {
    private static String username = null;
    public static State state = State.LOGGED_OUT;

    public PreLogin() {
    }

    public static String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login" -> signIn(params);
                case "register" -> register(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public static String signIn(String... params) throws Exception {
        if (params.length > 1) {
            state = State.LOGGED_IN;
            username = params[0];
            return String.format("Success! You signed in as %s.\n", username);
        }
        throw new Exception("Expected: <username> <password>\n");
    }

    public static String register(String... params) throws Exception {
        if (params.length >= 3) {
            state = State.LOGGED_IN;
            var username = params[0];
            var password = params[1];
            var email = params[2];
            // send it over to the server
            return String.format("You registered as %s and are now signed in\n", username);
        }
        throw new Exception("Expected: <username> <password> <email> \n");
    }

    public static String help() {
        return """
                    login <username> <password> - to play chess
                    register <username> <password> <email> - to create an account
                    help - possible commands
                    quit - exit chess
                """;
    }
}
