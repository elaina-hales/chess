package dataaccess;

import java.util.UUID;


public class SQLAuth implements AuthDAO{
    private static String generateToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String createAuth(String username) {
        String authToken = generateToken();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO User_Auth (Username, Authtoken) VALUES (?, ?)";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, authToken);

                preparedStatement.executeUpdate();
                return authToken;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getAuth(String authToken) {
        String username = null;
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT Username FROM User_Auth WHERE Authtoken=?";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, authToken);
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()){
                        username = rs.getString("Username");
                    }
                    return username;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAuth(String authToken) {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "DELETE FROM User_Auth WHERE Authtoken=?";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, authToken);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear(){
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "TRUNCATE User_Auth";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
