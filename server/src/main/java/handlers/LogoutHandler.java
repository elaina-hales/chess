package handlers;

import com.google.gson.Gson;
import dataaccess.*;
import requestsresults.*;
import service.*;
import spark.Request;
import spark.Response;


public class LogoutHandler {
    public String handleRequest(Request req, Response res, UserDAO user, AuthDAO userAuth, Gson gson) {
        try {
            LogoutRequest request = new LogoutRequest(req.headers("authorization"));
            UserService service = new UserService();
            LogoutResult result = service.logout(request, userAuth);
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
