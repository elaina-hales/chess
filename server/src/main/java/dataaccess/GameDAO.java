package dataaccess;
import model.*;
import service.AlreadyTakenException;
import service.BadRequestException;

import java.util.Collection;

public interface GameDAO {
     Collection<GameData> listGames();
     GameData getGame(int gameID);
     int createGame(String gameName);
     void updateGame(int gameID, String playerColor, String username) throws AlreadyTakenException, BadRequestException;
     void clear();
}
