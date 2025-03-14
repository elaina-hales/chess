package consoleRepl;

import menus.PostLogin;
import menus.PreLogin;

import java.util.Scanner;

import static consoleRepl.State.SIGNEDIN;


public class Repl {

    public void run() {
        System.out.println("Welcome to chess. Sign in to start.");
        System.out.print(PreLogin.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            String line = scanner.nextLine();
            try {
                result = PreLogin.eval(line);
                System.out.print(result);

                if (PreLogin.state == SIGNEDIN){
                    System.out.print(PostLogin.help());
                    result = PostLogin.eval(line);
                    System.out.print(result);
                }
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }
}
