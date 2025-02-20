package handlers;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import requestsresults.*;
import service.*;
import spark.Request;
import spark.Response;


public class LoginHandler {
    public String handleRequest(Request req, Response res, UserDAO user, AuthDAO userAuth) {
        Gson gson = new Gson();
        try {
            LoginRequest request = gson.fromJson(req.body(), LoginRequest.class);
            UserService service = new UserService();
            LoginResult result = service.login(request, user, userAuth);
            return gson.toJson(result);
        } catch (UnauthorizedException u) {
            res.status(401);
            Error err = new Error(u.getMessage());
            return gson.toJson(err);
        } catch (Exception e) {
            res.status(500);
            Error err = new Error(e.getMessage());
            return gson.toJson(err);
        }
    }
}
