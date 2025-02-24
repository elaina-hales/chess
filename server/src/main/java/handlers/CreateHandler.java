package handlers;

import com.google.gson.Gson;
import dataaccess.*;
import requestsresults.*;
import service.*;
import spark.Request;
import spark.Response;


public class CreateHandler extends ParentHandler {
    public String handleRequest(Request req, Response res, AuthDAO userAuth, GameDAO games, Gson gson) {
        try {
            String authToken = req.headers("authorization");
            CreateRequest request = gson.fromJson(req.body(), CreateRequest.class);
            GamesService service = new GamesService();
            if (request.gameName() == null) {
                throw new BadReqException("Error: bad request");
            } else {
                CreateResult result = service.create(request, authToken, userAuth, games);
                return gson.toJson(result);
            }
        }  catch (Exception e) {
            return handleError(res, e, gson);
        }
    }
}
