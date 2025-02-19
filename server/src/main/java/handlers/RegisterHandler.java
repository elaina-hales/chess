package handlers;

import dataaccess.DataAccessException;
import spark.Response;
import spark.Request;
import com.google.gson.Gson;
import requestsresults.*;
import service.*;


public class RegisterHandler {
    public String handleRequest(Request req, Response res) {
        Gson gson = new Gson();
        try {
            RegisterRequest request = gson.fromJson(req.body(), RegisterRequest.class);
            UserService service = new UserService();
            RegisterResult result = service.register(request);
            return gson.toJson(result);
        } catch (BadRequestExeception b) {
            res.status(400);
            res.body(gson.toJson("{ \"message\": \"Error: bad request\" }"));
            return gson.toJson(res);
        } catch (AlreadyTakenException e) {
            res.status(403);
            res.body(gson.toJson("{ \"message\": \"Error: already taken\" }"));
            return gson.toJson(res);
        } catch (Exception e) {
            res.status(500);
            res.body(gson.toJson(e.getMessage()));
            return gson.toJson(res);
        }
    }
}
