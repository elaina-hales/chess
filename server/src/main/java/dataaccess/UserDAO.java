package dataaccess;
import model.UserData;

public interface UserDAO {
     void createUser(UserData user);
     UserData getUser(String username);
     Boolean isPassMatch(String username, String password);
     void clear();
}
