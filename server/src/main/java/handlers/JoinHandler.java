package handlers;

import com.google.gson.Gson;
import dataaccess.*;
import requestsresults.*;
import service.GamesService;
import service.*;
import spark.Request;
import spark.Response;


public class JoinHandler {
    public String handleRequest(Request req, Response res, AuthDAO userAuth, GameDAO games) {
        Gson gson = new Gson();
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
        } catch (UnauthorizedException u) {
            res.status(401);
            Error err = new Error(u.getMessage());
            return gson.toJson(err);
        } catch (BadReqException b) {
            res.status(400);
            Error err = new Error(b.getMessage());
            return gson.toJson(err);
        } catch (AlreadyTakenException a) {
            res.status(403);
            Error err = new Error(a.getMessage());
            return gson.toJson(err);
        } catch (Exception e) {
            res.status(500);
            Error err = new Error(e.getMessage());
            return gson.toJson(err);
        }
    }
}
