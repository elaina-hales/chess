package dataaccess;

public interface AuthDAO {
     String createAuth(String username);
     String getAuth(String authToken);
}
