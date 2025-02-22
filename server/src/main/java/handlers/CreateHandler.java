package handlers;

import com.google.gson.Gson;
import dataaccess.*;
import requestsresults.*;
import service.*;
import spark.Request;
import spark.Response;


public class CreateHandler {
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
        } catch (UnauthorizedException u) {
            res.status(401);
            Error err = new Error(u.getMessage());
            return gson.toJson(err);
        } catch (BadReqException b) {
            res.status(400);
            Error err = new Error(b.getMessage());
            return gson.toJson(err);
        } catch (Exception e) {
            res.status(500);
            Error err = new Error(e.getMessage());
            return gson.toJson(err);
        }
    }
}
