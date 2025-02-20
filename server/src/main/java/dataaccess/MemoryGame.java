package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.*;


public class MemoryGame implements GameDAO{
    private final Collection<GameData> games = new ArrayList<>();
    @Override
    public Collection<GameData> listGames() {
        return games;
    }

    @Override
    public GameData getGame(String gameID) {
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
    public void updateGame(String gameID) {

    }
}
