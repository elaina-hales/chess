package repl;

import client.ServerFacade;
import menus.GameMenu;
import menus.PostLogin;
import menus.PreLogin;

import java.util.Scanner;

import static repl.GameState.JOINED_GAME;
import static repl.GameState.NOT_JOINED;
import static repl.State.LOGGED_IN;
import static repl.State.LOGGED_OUT;


public class Repl {
    private State state = LOGGED_OUT;
    private GameState joined = NOT_JOINED;
    private String authToken = null;

    public void run(ServerFacade server) {
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
                        result = GameMenu.eval(line, server);
                        System.out.print(result);
                        joined = GameMenu.joined;
                    } else {
                        result = PostLogin.eval(line, server, authToken);
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
