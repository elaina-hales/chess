package service;

import dataaccess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestsresults.*;
import model.*;

class ClearServiceTest {
    private ClearService service;
    private ClearResult expected;


    @BeforeEach
    void setUp() {
        service = new ClearService();
        expected = new ClearResult();
    }

    @Test
    void testClear() {
        UserDAO user = new MemoryUser();
        UserData tmpUser = new UserData("test", "password", "test@gmail.com");
        user.createUser(tmpUser);

        GameDAO game = new MemoryGame();
        game.createGame("test_game");
        game.createGame("test_game2");

        AuthDAO auth = new MemoryAuth();
        auth.createAuth("test");

        ClearResult actual = service.clear(user, auth, game);
        Assertions.assertEquals(expected, actual);
    }
}