package menus;

import client.ReturnObject;
import client.ServerFacade;
import repl.State;
import websocket.ServerMessageObserver;

import java.util.Arrays;
import java.util.Map;

public class PreLogin {
    public static String username = null;
    public static State state = State.LOGGED_OUT;
    private static String authToken = "";
    private static ServerFacade server;
    private String serverUrl;


    public PreLogin(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login" -> signIn(server, params);
                case "register" -> register(server, params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public static String signIn(ServerFacade server, String... params) throws Exception {
        if (params.length > 1) {
            username = params[0];
            String password = params[1];
            ReturnObject r = server.login(username, password);
            if (r.statusCode() == 200){
                state = State.LOGGED_IN;
                Map<String, String> body = r.body();
                authToken = body.get("authToken");
            }
            return switch (r.statusCode()) {
                case 200 -> String.format("Success! You signed in as %s.\n", username);
                case 401 -> "Log in unsuccessful. Please try again.\n";
                default -> "Server Error: " + r.statusMessage() + "\n";
            };
        }
        throw new Exception("Expected: <username> <password>\n");
    }

    public static String register(ServerFacade server, String... params) throws Exception {
        if (params.length >= 3) {
            username = params[0];
            var password = params[1];
            var email = params[2];
            ReturnObject r = server.register(username, password, email);
            if (r.statusCode() == 200){
                state = State.LOGGED_IN;
                Map<String, String> o = r.body();
                authToken = o.get("authToken");
            }
            return switch (r.statusCode()) {
                case 200 -> String.format("You registered as %s and are now signed in\n", username);
                case 400 -> "Error: bad request";
                case 403 -> String.format("Register failed. '%s' is already taken. \n", username);
                default -> r.statusMessage();
            };
        }
        throw new Exception("Expected: <username> <password> <email> \n");
    }
    public static String getUsername(){
        return username;
    }
    public static String getToken(){
        return authToken;
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
