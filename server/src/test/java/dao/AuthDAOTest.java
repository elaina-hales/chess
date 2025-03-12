package dao;

import chess.ChessGame;
import dataaccess.*;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthDAOTest {
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
    void testCreateAuthDAOSuccess() {
        String authtoken = auth.createAuth("user1");
        Assertions.assertNotNull(authtoken);
    }

    @Test
    void testCreateAuthNullUser() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            auth.createAuth(null);
        });
    }


//    @Test
//    void testGetGameSuccess(){
//        int gameID = game.createGame("test1");
//
//        GameData actual = game.getGame(gameID);
//        GameData expected = new GameData(gameID, null, null, "test1", new ChessGame());
//        Assertions.assertEquals(actual, expected);
//    }
//
//    @Test
//    void testGetNonExistentGame(){
//        game.createGame("test1");
//
//        GameData actual = game.getGame(0);
//        Assertions.assertNull(actual);
//    }
//
//    @Test
//    void createGameDAOSuccess(){
//        int gameID = game.createGame("test");
//        GameData gottenGame = game.getGame(gameID);
//
//        Assertions.assertNotNull(gottenGame);
//
//    }
//
//    @Test
//    void createGameDAONullGame(){
//        Assertions.assertThrows(RuntimeException.class, () -> {
//            game.createGame(null);
//        });
//    }
//
//    @Test
//    void testUpdateGameDAOSuccess() throws BadReqException, AlreadyTakenException {
//        int gameID = game.createGame("test");
//        game.updateGame(gameID, "BLACK", "user");
//        GameData actual = game.getGame(gameID);
//
//        Assertions.assertEquals("user", actual.blackUsername());
//    }
//
//    @Test
//    void testUpdateGameDAOUserAlreadyTaken() throws BadReqException, AlreadyTakenException {
//        int gameID = game.createGame("test");
//        game.updateGame(gameID, "BLACK", "user");
//        Assertions.assertThrows(AlreadyTakenException.class, () -> {
//            game.updateGame(gameID, "BLACK", "another user");
//        });
//    }
}