package handlers;

import com.google.gson.Gson;
import dataaccess.*;
import requestsresults.*;
import service.*;
import spark.Request;
import spark.Response;


public class ListHandler {
    public String handleRequest(Request req, Response res, AuthDAO userAuth, GameDAO game) {
        Gson gson = new Gson();
        try {
            GamesRequest request = new GamesRequest(req.headers("authorization"));
            GamesService service = new GamesService();
            GamesResult result = service.listGames(request, userAuth, game);
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
