package dataaccess;

import chess.ChessGame;
import model.GameData;
import service.AlreadyTakenException;

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
    public GameData createGame(String gameName) {
        ChessGame game = new ChessGame();
        GameData newGame = new GameData(games.size()+1, "", "", gameName, game);
        games.add(newGame);
        return newGame;
    }

    @Override
    public void updateGame(int gameID, String playerColor, String username) throws AlreadyTakenException {
        GameData game = getGame(gameID);
        if (playerColor.equals("Black") || playerColor.equals("black") || playerColor.equals("BLACK")) {
            if (game.blackUsername() == null){
                GameData updatedGame = new GameData(gameID, game.whiteUsername(), username, game.gameName(), game.game());
                games.add(updatedGame);
                games.remove(game);
            } else {
                throw new AlreadyTakenException("Error: Already Taken");
            }
        }
    }
}
