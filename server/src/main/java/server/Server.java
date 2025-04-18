package server;
import com.google.gson.Gson;
import dataaccess.*;
import handlers.*;
import server.websocket.WebSocketHandler;
import spark.*;


public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.webSocket("/ws", new WebSocketHandler());


        try {
            createRoutes();
        } catch(DataAccessException d){
            System.out.println(d.getMessage());
        }

        //This line initializes the server and can be removed once you have a functioning endpoint
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private static void createRoutes() throws DataAccessException {
        DatabaseManager.configureDatabase();
        UserDAO user = new SQLUser();
        AuthDAO userAuth = new SQLAuth();
        GameDAO game = new SQLGame();
        Gson gson = new Gson();
        Spark.post("/user", (req, res) -> (new RegisterHandler()).handleRequest(req, res, user, userAuth, gson));
        Spark.post("/session", (req, res) -> (new LoginHandler()).handleRequest(req, res, user, userAuth, gson));
        Spark.delete("/session", (req, res) -> (new LogoutHandler()).handleRequest(req, res, user, userAuth, gson));
        Spark.get("/game", (req, res) -> (new ListHandler().handleRequest(req, res, userAuth, game, gson)));
        Spark.post("/game", (req, res) -> (new CreateHandler().handleRequest(req, res, userAuth, game, gson)));
        Spark.put("/game", (req, res) -> (new JoinHandler().handleRequest(req, res, userAuth, game, gson)));
        Spark.delete("/db", (req, res) -> (new ClearHandler().handleRequest(res, user, userAuth, game, gson)));
    }
}
