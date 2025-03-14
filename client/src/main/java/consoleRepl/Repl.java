package consoleRepl;

import menus.PostLogin;
import menus.PreLogin;

import java.util.Scanner;

import static consoleRepl.State.*;


public class Repl {
    private State state = LOGGED_OUT;

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
                    result = PostLogin.eval(line);
                    System.out.print(result);
                    state = PostLogin.state;
                    PreLogin.state = state;
                } else {
                    result = PreLogin.eval(line);
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
