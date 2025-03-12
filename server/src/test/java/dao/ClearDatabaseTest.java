package dao;

import dataaccess.*;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

class ClearDatabaseTest {
    private GameDAO game;
    private UserDAO user;
    private AuthDAO auth;


    @BeforeEach
    void setUp() {
        user = new SQLUser();
        game = new SQLGame();
        auth = new SQLAuth();
    }

    @Test
    void testClearUser() {
        UserData tmpUser = new UserData("test", "password", "test@gmail.com");
        user.createUser(tmpUser);

        user.clear();
        Collection<String> actual = new ArrayList<>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM User";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()){
                        actual.add(rs.getString("Username"));
                        actual.add(rs.getString("Password"));
                        actual.add(rs.getString("Email"));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void testClearAuth() {
        auth.createAuth("test");

        auth.clear();
        Collection<String> actual = new ArrayList<>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM User_Auth";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()){
                        actual.add(rs.getString("Username"));
                        actual.add(rs.getString("Authtoken"));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void testClearGame() {
        game.createGame("test_game");
        game.createGame("test_game2");

        game.clear();
        Collection<String> actual = new ArrayList<>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM Game";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()){
                        actual.add(rs.getString("GameID"));
                        actual.add(rs.getString("WhiteUsername"));
                        actual.add(rs.getString("BlackUsername"));
                        actual.add(rs.getString("GameName"));
                        actual.add(rs.getString("ChessGame"));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(actual.isEmpty());
    }
}