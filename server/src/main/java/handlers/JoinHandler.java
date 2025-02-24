package handlers;

import com.google.gson.Gson;
import dataaccess.*;
import requestsresults.*;
import service.GamesService;
import service.*;
import spark.Request;
import spark.Response;


public class JoinHandler extends ParentHandler{
    public String handleRequest(Request req, Response res, AuthDAO userAuth, GameDAO games, Gson gson) {
        try {
            String authToken = req.headers("authorization");
            JoinRequest request = gson.fromJson(req.body(), JoinRequest.class);
            GamesService service = new GamesService();
            if (request.playerColor() == null) {
                throw new BadReqException("Error: bad request");
            } else {
                JoinResult result = service.join(request, authToken, userAuth, games);
                return gson.toJson(result);
            }
        }  catch (Exception e) {
            return handleError(res, e, gson);
        }
    }
}
