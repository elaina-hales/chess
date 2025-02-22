package handlers;

import dataaccess.*;
import spark.Response;
import spark.Request;
import com.google.gson.Gson;
import requestsresults.*;
import service.*;


public class RegisterHandler {
    public String handleRequest(Request req, Response res, UserDAO user, AuthDAO userAuth) {
        Gson gson = new Gson();
        try {
            RegisterRequest request = gson.fromJson(req.body(), RegisterRequest.class);
            UserService service = new UserService();
            RegisterResult result = service.register(request, user, userAuth);
            return gson.toJson(result);
        } catch (BadReqException b) {
            res.status(400);
            Error err = new Error(b.getMessage());
            return gson.toJson(err);
        } catch (AlreadyTakenException a) {
            res.status(403);
            Error err = new Error(a.getMessage());
            return gson.toJson(err);
        } catch (Exception e) {
            res.status(500);
            Error err = new Error(e.getMessage());
            return gson.toJson(err);
        }
    }
}
