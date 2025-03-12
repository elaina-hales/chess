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
}