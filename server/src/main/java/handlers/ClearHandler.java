package handlers;

import com.google.gson.Gson;
import dataaccess.*;
import requestsresults.*;
import service.*;
import spark.Response;


public class ClearHandler extends ParentHandler {
    public String handleRequest(Response res, UserDAO user, AuthDAO userAuth, GameDAO games, Gson gson) {
        try {
            ClearService service = new ClearService();
            ClearResult result = service.clear(user, userAuth, games);
            return gson.toJson(result);
        }  catch (Exception e) {
            return handleError(res, e, gson);
        }
    }
}
