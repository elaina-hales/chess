package service;

import dataaccess.*;
import model.*;
import requestsresults.*;

import java.util.Collection;


public class GamesService {
    public GamesResult listGames(GamesRequest request, AuthDAO userAuth, GameDAO games) throws UnauthorizedException {
        String authToken = request.authToken();
        String username = userAuth.getAuth(authToken);
        if (username == null){
            throw new UnauthorizedException("Error: unauthorized");
        } else {
            Collection<GameData> listedGames = games.listGames();
            return new GamesResult(listedGames);
        }
    }
}
