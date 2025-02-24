package handlers;

import dataaccess.*;
import spark.Response;
import spark.Request;
import com.google.gson.Gson;
import requestsresults.*;
import service.*;


public class RegisterHandler extends ParentHandler{
    public String handleRequest(Request req, Response res, UserDAO user, AuthDAO userAuth, Gson gson) {
        try {
            RegisterRequest request = gson.fromJson(req.body(), RegisterRequest.class);
            UserService service = new UserService();
            RegisterResult result = service.register(request, user, userAuth);
            return gson.toJson(result);
        } catch (Exception e) {
            return handleError(res, e, gson);
        }
    }
}
