package handlers;

import spark.Response;
import spark.Request;
import com.google.gson.Gson;
import requestsresults.*;
import service.*;


public class RegisterHandler {
    public static String handleRequest(Request req, Response res) {
        Gson gson = new Gson();
        RegisterRequest request = gson.fromJson(req.body(), RegisterRequest.class);
        try {
            UserService service = new UserService();
            RegisterResult result = service.register(request);
            return gson.toJson(result);
        } catch () {
            res.status(400);

        } catch () {
            res.status(500);
        } catch (AlreadyTakenException e) {
            res.status(403);
            res.body(gson.toJson("{ \"message\": \"Error: bad request\" }"));

        }
    }
}
