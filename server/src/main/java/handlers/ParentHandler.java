package handlers;

import com.google.gson.Gson;
import service.AlreadyTakenException;
import service.BadReqException;
import service.UnauthorizedException;
import spark.Response;

import java.util.Objects;

public class ParentHandler {
    public String handleError(Response res, Exception u, Gson gson){
        if (Objects.equals(u.getMessage(), "Error: already taken")){
            res.status(403);
        } else if (u.getMessage().equals("Error: unauthorized")) {
            res.status(401);
        } else if (u.getMessage().equals("Error: bad request")){
            res.status(400);
        } else {
            res.status(500);
        }
        Error err = new Error(u.getMessage());
        return gson.toJson(err);

    }
}
