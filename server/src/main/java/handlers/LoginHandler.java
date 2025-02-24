package handlers;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import requestsresults.*;
import service.*;
import spark.Request;
import spark.Response;


public class LoginHandler extends ParentHandler {
    public String handleRequest(Request req, Response res, UserDAO user, AuthDAO userAuth, Gson gson) {
        try {
            LoginRequest request = gson.fromJson(req.body(), LoginRequest.class);
            UserService service = new UserService();
            LoginResult result = service.login(request, user, userAuth);
            return gson.toJson(result);
        } catch (Exception e) {
            return handleError(res, e, gson);
        }
    }
}
