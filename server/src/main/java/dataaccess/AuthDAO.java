package dataaccess;
import model.UserData;

public interface AuthDAO {
     void createAuth(UserData user);
     UserData getUser(String username);
}
