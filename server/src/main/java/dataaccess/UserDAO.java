package dataaccess;
import model.UserData;

public interface UserDAO {
     void createUser(UserData user);
     UserData getUser(String username);
     Boolean passMatch(String username, String password);
}
