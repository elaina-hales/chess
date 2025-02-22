package handlers;

import com.google.gson.Gson;
import dataaccess.*;
import requestsresults.*;
import service.*;
import spark.Response;


public class ClearHandler {
    public String handleRequest(Response res, UserDAO user, AuthDAO userAuth, GameDAO games) {
        Gson gson = new Gson();
        try {
            ClearService service = new ClearService();
            ClearResult result = service.clear(user, userAuth, games);
            return gson.toJson(result);
        } catch (Exception e) {
            res.status(500);
            Error err = new Error(e.getMessage());
            return gson.toJson(err);
        }
    }
}
