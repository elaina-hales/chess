package dataaccess;

import model.GameData;
import service.AlreadyTakenException;
import service.BadReqException;

import java.util.Collection;

public interface GameDAO {
     Collection<GameData> listGames();
     GameData getGame(int gameID);
     int createGame(String gameName);
     void updateGame(int gameID, String playerColor, String username) throws AlreadyTakenException, BadReqException;
     void clear();
}
