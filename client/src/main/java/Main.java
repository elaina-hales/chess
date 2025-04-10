import client.ServerFacade;
import exception.ResponseException;
import repl.Repl;

public class Main {
    public static void main(String[] args) throws ResponseException {
        new Repl("http://localhost:8080/").run();
    }
}