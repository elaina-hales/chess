package consoleRepl;

import menus.PostLogin;
import menus.PreLogin;

import java.util.Scanner;

import static consoleRepl.State.*;


public class Repl {

    public void run() {
        System.out.println("Welcome to chess! Sign in to start.");
        System.out.print(PreLogin.help());
        System.out.print("[LOGGED_OUT] >>> ");

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            String line = scanner.nextLine();
            try {
                if (PreLogin.state == LOGGED_IN) {
                    System.out.print(PostLogin.help());
                    result = PostLogin.eval(line);
                    System.out.print(result);
                } else if (PreLogin.state == LOGGED_OUT){
                    result = PreLogin.eval(line);
                    System.out.print(result);
                }
                System.out.print(String.format("[%s] >>> ", PreLogin.state));
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }
}
