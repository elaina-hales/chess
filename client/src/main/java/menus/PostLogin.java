package menus;

import consoleRepl.State;

import java.util.Arrays;

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
                case "create" -> register(params);
                case "list" -> register(params);
                case "play" -> register(params);
                case "observe" -> register(params);
                case "logout" -> logOut(params);
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public static String logOut(String... params) {
        state = State.LOGGED_OUT;
        PreLogin.state = State.LOGGED_OUT;
        return "Success! You have now logged out.\n";
    }

    public static String register(String... params) throws Exception {
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
                    create <game name>
                    list
                    play <game name> 
                    observe <game name>
                    logout
                """;
    }
}
