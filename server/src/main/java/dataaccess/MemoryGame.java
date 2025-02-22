package dataaccess;

import chess.ChessGame;
import model.GameData;
import service.AlreadyTakenException;
import service.BadRequestException;

import java.util.*;


public class MemoryGame implements GameDAO{
    private final Collection<GameData> games = new ArrayList<>();
    @Override
    public Collection<GameData> listGames() {
        return games;
    }

    @Override
    public GameData getGame(int gameID) {
        for (GameData game : games){
            int currentID = game.gameID();
            if (currentID == gameID) {
                return game;
            }
        }
        return null;
    }

    @Override
    public int createGame(String gameName) {
        ChessGame game = new ChessGame();
        int gameID = games.size()+1;
        GameData newGame = new GameData(gameID, null, null, gameName, game);
        games.add(newGame);
        return gameID;
    }

    @Override
    public void updateGame(int gameID, String playerColor, String username) throws AlreadyTakenException, BadRequestException {
        GameData game = getGame(gameID);
        if (playerColor.equals("Black") || playerColor.equals("black") || playerColor.equals("BLACK")) {
            if (game.blackUsername() == null) {
                GameData updatedGame = new GameData(gameID, game.whiteUsername(), username, game.gameName(), game.game());
                games.add(updatedGame);
                games.remove(game);
            } else {
                throw new AlreadyTakenException("Error: already taken");
            }
        } else if (playerColor.equals("White") || playerColor.equals("white") || playerColor.equals("WHITE")) {
            if (game.whiteUsername() == null) {
                GameData updatedGame = new GameData(gameID, username, game.blackUsername(), game.gameName(), game.game());
                games.add(updatedGame);
                games.remove(game);
            } else {
                throw new AlreadyTakenException("Error: already taken");
            }
        } else {
            throw new BadRequestException("Error: bad request");
        }
    }

    @Override
    public void clear(){
        games.clear();
    }
}
