package repl;

import menus.GameMenu;
import menus.PostLogin;
import menus.PreLogin;
import websocket.ServerMessageObserver;
import websocket.messages.ServerMessage;

import java.util.Scanner;

import static repl.GameState.JOINED_GAME;
import static repl.GameState.NOT_JOINED;
import static repl.State.LOGGED_IN;
import static repl.State.LOGGED_OUT;


public class Repl {
    private State state = LOGGED_OUT;
    private GameState joined = NOT_JOINED;
    private String authToken = null;
    private String username = null;
    private ServerMessageObserver serverMessageObserver;
    private final GameMenu gameMenu;
    private final PostLogin postMenu;
    private final PreLogin preMenu;


    public Repl(String serverURL){
        gameMenu = new GameMenu(serverURL);
        postMenu = new PostLogin(serverURL);
        preMenu = new PreLogin(serverURL);
    }

    public void run() {
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
                        result = gameMenu.eval(line, username, authToken);
                        joined = gameMenu.joined;
                        System.out.print(result);
                    } else {
                        result = postMenu.eval(line, authToken, username);
                        System.out.print(result);
                        state = postMenu.state;
                        preMenu.state = state;
                        joined = postMenu.joined;
                    }
                } else {
                    result = preMenu.eval(line);
                    System.out.print(result);
                    state = preMenu.state;
                    postMenu.state = state;
                    if (state == LOGGED_IN){
                        this.username = preMenu.getUsername();
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
}
