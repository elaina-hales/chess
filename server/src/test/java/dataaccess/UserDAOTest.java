package dataaccess;

import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

class UserDAOTest {
    private UserDAO user;
    private AuthDAO auth;

    @BeforeEach
    void setUp() {
        user = new SQLUser();
        auth = new SQLAuth();
    }

    @AfterEach
    void tearDown() {
        user.clear();
        auth.clear();
    }

    @Test
    void testCreateUserDAOSuccess() {
        UserData tmpUser = new UserData("test", "pass", "random email");
        user.createUser(tmpUser);
        UserData actual = user.getUser("test");
        Assertions.assertNotNull(actual);
    }

    @Test
    void testCreateUserMissingPiecesDAO() {
        UserData tmpUser = new UserData(null, "pass", "random email");
        Assertions.assertThrows(RuntimeException.class, () -> {
            user.createUser(tmpUser);
        });
    }

    @Test
    void testGetUserDAOSuccess(){
        UserData tmpUser = new UserData("test", "pass", "random email");
        user.createUser(tmpUser);
        UserData actual = user.getUser("test");
        Assertions.assertNotNull(actual);
    }

    @Test
    void testGetNonExistentUserDAO(){
        UserData actual = user.getUser("test");
        Assertions.assertNull(actual);
    }

    @Test
    void testCheckPasswordSuccess(){
        UserData tmpUser = new UserData("test", "pass", "random email");
        user.createUser(tmpUser);

        Assertions.assertTrue(user.isPassMatch("test", "pass"));
    }

    @Test
    void testCheckPasswordFalseDAO(){
        UserData tmpUser = new UserData("test", "pass", "random email");
        user.createUser(tmpUser);

        Assertions.assertFalse(user.isPassMatch("test", "password"));
    }



//    @Test
//    void testCreateAuthNullUser() {
//        Assertions.assertThrows(RuntimeException.class, () -> {
//            auth.createAuth(null);
//        });
//    }
//
//    @Test
//    void testGetAuthDAOSuccess() {
//        String authtoken = auth.createAuth("user1");
//        String gottenAuthtoken = auth.getAuth(authtoken);
//
//        Assertions.assertNotNull(gottenAuthtoken);
//    }
//
//    @Test
//    void testGetNonExistentAuthDAO() {
//        String gottenAuthtoken = auth.getAuth("akjhfkjaf aljfha not valid");
//        Assertions.assertNull(gottenAuthtoken);
//    }
//
//
//    @Test
//    void testDeleteAuthDAOSuccess() {
//        String authtoken = auth.createAuth("user1");
//        auth.deleteAuth(authtoken);
//        Assertions.assertNull(auth.getAuth(authtoken));
//    }
//
//    @Test
//    void testDeleteAuthNonExistentDAO() {
//
//        Collection<String> previous = new ArrayList<>();
//        try (var conn = DatabaseManager.getConnection()) {
//            var statement = "SELECT * FROM User_Auth";
//            try (var preparedStatement = conn.prepareStatement(statement)) {
//                try (var rs = preparedStatement.executeQuery()) {
//                    while (rs.next()){
//                        String username = rs.getString("Username");
//                        String authtoken = rs.getString("Authtoken");
//
//                        previous.add(username);
//                        previous.add(authtoken);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        auth.deleteAuth("kjshdk this does not exist");
//
//        Collection<String> current = new ArrayList<>();
//        try (var conn = DatabaseManager.getConnection()) {
//            var statement = "SELECT * FROM User_Auth";
//            try (var preparedStatement = conn.prepareStatement(statement)) {
//                try (var rs = preparedStatement.executeQuery()) {
//                    while (rs.next()){
//                        String username = rs.getString("Username");
//                        String authtoken = rs.getString("Authtoken");
//
//                        current.add(username);
//                        current.add(authtoken);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        Assertions.assertEquals(previous, current);
//    }
}