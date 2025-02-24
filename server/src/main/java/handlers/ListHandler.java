package handlers;

import com.google.gson.Gson;
import dataaccess.*;
import requestsresults.*;
import service.*;
import spark.Request;
import spark.Response;


public class ListHandler extends ParentHandler {
    public String handleRequest(Request req, Response res, AuthDAO userAuth, GameDAO game, Gson gson) {
        try {
            GamesRequest request = new GamesRequest(req.headers("authorization"));
            GamesService service = new GamesService();
            GamesResult result = service.listGames(request, userAuth, game);
            return gson.toJson(result);
        }  catch (Exception e) {
            return handleError(res, e, gson);
        }
    }
}
