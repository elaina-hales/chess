package server;
import dataaccess.AuthDAO;
import dataaccess.MemoryAuth;
import dataaccess.MemoryUser;
import dataaccess.UserDAO;
import handlers.LoginHandler;
import handlers.RegisterHandler;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        createRoutes();

        //This line initializes the server and can be removed once you have a functioning endpoint
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private static void createRoutes() {
        UserDAO user = new MemoryUser();
        AuthDAO userAuth = new MemoryAuth();
        Spark.post("/user", (req, res) -> (new RegisterHandler()).handleRequest(req, res, user, userAuth));
        Spark.post("/session", (req, res) -> (new LoginHandler()).handleRequest(req, res, user, userAuth) );
//        Spark.delete("/session", (req, res) -> );
//        Spark.get("/game", (req, res) -> handler);
//        Spark.post("/game", (req, res) -> handler);
//        Spark.put("/game", (req, res) -> handler);
//        Spark.delete("/db", (req, res) -> handler);
    }

}
