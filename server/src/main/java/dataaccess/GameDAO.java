package dataaccess;
import model.*;
import service.AlreadyTakenException;

import java.util.Collection;

public interface GameDAO {
     Collection<GameData> listGames();
     GameData getGame(int gameID);
     int createGame(String gameName);
     void updateGame(int gameID, String playerColor, String username) throws AlreadyTakenException;
     void clear();
}
