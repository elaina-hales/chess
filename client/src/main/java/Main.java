import client.ServerFacade;
import repl.Repl;

public class Main {
    public static void main(String[] args) {
        new Repl("http://localhost:8080/").run();
    }
}