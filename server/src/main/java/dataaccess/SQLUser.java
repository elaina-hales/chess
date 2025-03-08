package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;


public class SQLUser implements UserDAO{

    @Override
    public void createUser(UserData user) {
        String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());
        String username = user.username();
        String email = user.email();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO User (Username, Password, Email) VALUES (?, ?, ?)";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, hashedPassword);
                preparedStatement.setString(3, email);

                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserData getUser(String username) {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT Username, password, email FROM User WHERE Username=?";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, username);
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        String u = rs.getString("Username");
                        String p = rs.getString("Password");
                        String e = rs.getString("Email");

                        return new UserData(u, p, e);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean isPassMatch(String username, String password) {
        UserData user = getUser(username);
        return user.password().equals(password);
    }

    @Override
    public void clear(){
        users.clear();
    }
}
