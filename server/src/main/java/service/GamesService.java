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

    public JoinResult join(JoinRequest request, String authToken, AuthDAO userAuth, GameDAO games) throws UnauthorizedException, BadReqException, AlreadyTakenException {
        String username = userAuth.getAuth(authToken);
        if (username == null){
            throw new UnauthorizedException("Error: unauthorized");
        } else {
            String playerColor = request.playerColor();
            int gameID = request.gameID();
            GameData game = games.getGame(gameID);
            if (game != null) {
                games.updateGame(gameID, playerColor, username);
                return new JoinResult();
            } else {
                throw new BadReqException("Error: bad request");
            }
        }
    }

    public CreateResult create(CreateRequest request, String authToken, AuthDAO userAuth, GameDAO games) throws UnauthorizedException {
        String username = userAuth.getAuth(authToken);
        if (username == null) {
            throw new UnauthorizedException("Error: unauthorized");
        } else {
            String gameName = request.gameName();
            int gameID = games.createGame(gameName);
            return new CreateResult(gameID);
        }
    }
}
