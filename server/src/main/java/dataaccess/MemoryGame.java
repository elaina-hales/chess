package dataaccess;

import chess.ChessGame;
import model.GameData;
import service.AlreadyTakenException;
import service.BadReqException;

import java.util.ArrayList;
import java.util.Collection;


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
    public void updateGame(int gameID, String color, String username) throws AlreadyTakenException, BadReqException {
        GameData game = getGame(gameID);
        if (color.equals("BLACK")) {
            if (game.blackUsername() == null) {
                String wUser = game.whiteUsername();
                String gameName = game.gameName();
                GameData updatedGame = new GameData(gameID, wUser, username, gameName, game.game());
                games.add(updatedGame);
                games.remove(game);
            } else {
                throw new AlreadyTakenException("Error: already taken");
            }
        } else if (color.equals("WHITE")) {
            if (game.whiteUsername() == null) {
                String bUser = game.blackUsername();
                String gameName = game.gameName();
                GameData updatedGame = new GameData(gameID, username, bUser, gameName, game.game());
                games.add(updatedGame);
                games.remove(game);
            } else {
                throw new AlreadyTakenException("Error: already taken");
            }
        } else {
            throw new BadReqException("Error: bad request");
        }
    }

    @Override
    public void clear(){
        games.clear();
    }

    @Override
    public void updateGameNewMove(ChessGame chess, Integer gameID) {
    }
}
