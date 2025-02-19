package server;
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

        Spark.post("/user", (req, res) -> (new RegisterHandler()).handleRequest(req, res));
//        Spark.post("/session", (req, res) -> );
//        Spark.delete("/session", (req, res) -> );
//        Spark.get("/game", (req, res) -> handler);
//        Spark.post("/game", (req, res) -> handler);
//        Spark.put("/game", (req, res) -> handler);
//        Spark.delete("/db", (req, res) -> handler);
    }

}
