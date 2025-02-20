package dataaccess;
import model.*;

import java.util.Collection;

public interface GameDAO {
     Collection<GameData> listGames();
     GameData getGame(String gameID);
     GameData createGame(String gameName);
     void updateGame(String gameID);
}
