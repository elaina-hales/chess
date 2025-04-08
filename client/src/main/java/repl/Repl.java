package repl;

import client.ServerFacade;
import exception.ResponseException;
import menus.GameMenu;
import menus.PostLogin;
import menus.PreLogin;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;
import websocket.messages.ServerMessage;

import java.util.Scanner;

import static repl.GameState.JOINED_GAME;
import static repl.GameState.NOT_JOINED;
import static repl.State.LOGGED_IN;
import static repl.State.LOGGED_OUT;


public class Repl implements NotificationHandler {
    private State state = LOGGED_OUT;
    private GameState joined = NOT_JOINED;
    private String authToken = null;
    private String username = null;
    private WebSocketFacade ws = null;
    private NotificationHandler notificationHandler;

    public void run(ServerFacade server) {
        try {
            this.ws = new WebSocketFacade(server.getURL(), notificationHandler);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Welcome to chess! Sign in to start.");
        System.out.print(PreLogin.help());
        System.out.print("[LOGGED_OUT] >>> ");

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            String line = scanner.nextLine();
            try {
                if (state == LOGGED_IN) {
                    authToken = PreLogin.getToken();
                    if (joined == JOINED_GAME){
                        result = GameMenu.eval(line, server, username, ws);
                        joined = GameMenu.joined;
                        System.out.print(result);
                    } else {
                        result = PostLogin.eval(line, server, authToken, ws, username);
                        System.out.print(result);
                        state = PostLogin.state;
                        PreLogin.state = state;
                        joined = PostLogin.joined;
                    }
                } else {
                    result = PreLogin.eval(line, server);
                    System.out.print(result);
                    state = PreLogin.state;
                    PostLogin.state = state;
                    if (state == LOGGED_IN){
                        this.username = PreLogin.getUsername();
                    }
                }
                if (result.equals("quit")){
                    break;
                } else {
                    System.out.print(String.format("[%s] >>> ", state));
                }
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    @Override
    public void notify(ServerMessage notification) {
        System.out.println(notification.toString());
    }
}
