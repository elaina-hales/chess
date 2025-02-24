package handlers;

import com.google.gson.Gson;
import dataaccess.*;
import requestsresults.*;
import service.*;
import spark.Request;
import spark.Response;


public class LogoutHandler extends ParentHandler{
    public String handleRequest(Request req, Response res, UserDAO user, AuthDAO userAuth, Gson gson) {
        try {
            LogoutRequest request = new LogoutRequest(req.headers("authorization"));
            UserService service = new UserService();
            LogoutResult result = service.logout(request, userAuth);
            return gson.toJson(result);
        }  catch (Exception e) {
            return handleError(res, e, gson);
        }
    }
}
