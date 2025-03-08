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
        String userResult = null;
        String passResult = null;
        String emailResult = null;
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT Username, Password, Email FROM User WHERE Username=?";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, username);
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()){
                        userResult = rs.getString("Username");
                        passResult = rs.getString("Password");
                        emailResult = rs.getString("Email");
                    }
                    if ((userResult == null) && (passResult == null) && (emailResult == null)){
                        return null;
                    }
                    return new UserData(userResult, passResult, emailResult);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean isPassMatch(String username, String password) {
        UserData user = getUser(username);
        String hashedPassword = user.password();
        return BCrypt.checkpw(password, hashedPassword);
    }

    @Override
    public void clear(){
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "TRUNCATE User";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
