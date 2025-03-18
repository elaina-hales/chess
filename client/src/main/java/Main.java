import client.ServerFacade;
import repl.Repl;

public class Main {
    public static void main(String[] args) {
        new Repl().run(new ServerFacade("http://localhost:8080/"));
    }
}