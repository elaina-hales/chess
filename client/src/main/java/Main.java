import chess.*;
import client.ServerFacade;
import consoleRepl.Repl;

public class Main {
    public static void main(String[] args) {
        new Repl().run(new ServerFacade("http://localhost:8080/"));
    }
}