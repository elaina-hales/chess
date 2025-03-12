package dao;

import chess.ChessGame;
import dataaccess.*;
import model.GameData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.AlreadyTakenException;
import service.BadReqException;

import java.util.Collection;

class AuthDAOTest {
    private GameDAO game;
    private UserDAO user;
    private AuthDAO auth;

    @BeforeEach
    void setUp() {
        game = new SQLGame();
        user = new SQLUser();
        auth = new SQLAuth();
    }

    @AfterEach
    void tearDown() {
        game.clear();
        user.clear();
        auth.clear();
    }

    @Test
    void testListGamesDAOSuccess() {
        game.createGame("test1");
        game.createGame("test2");

        boolean correct = false;
        Collection<GameData> actual = game.listGames();
        for (GameData tmp: actual){
            if ((tmp.gameName().equals("test1")) || (tmp.gameName().equals("test2"))){
                correct = true;
            } else {
                correct = false;
                break;
            }
        }

        Assertions.assertTrue(correct);
    }

    // still neeed a bad case

    @Test
    void testGetGameSuccess(){
        int gameID = game.createGame("test1");

        GameData actual = game.getGame(gameID);
        GameData expected = new GameData(gameID, null, null, "test1", new ChessGame());
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void testGetNonExistentGame(){
        game.createGame("test1");

        GameData actual = game.getGame(0);
        Assertions.assertNull(actual);
    }

    @Test
    void createGameDAOSuccess(){
        int gameID = game.createGame("test");
        GameData gottenGame = game.getGame(gameID);

        Assertions.assertNotNull(gottenGame);

    }

    @Test
    void createGameDAONullGame(){
        Assertions.assertThrows(RuntimeException.class, () -> {
            game.createGame(null);
        });
    }

    @Test
    void testUpdateGameDAOSuccess() throws BadReqException, AlreadyTakenException {
        int gameID = game.createGame("test");
        game.updateGame(gameID, "BLACK", "user");
        GameData actual = game.getGame(gameID);

        Assertions.assertEquals("user", actual.blackUsername());
    }

    @Test
    void testUpdateGameDAOUserAlreadyTaken() throws BadReqException, AlreadyTakenException {
        int gameID = game.createGame("test");
        game.updateGame(gameID, "BLACK", "user");
        Assertions.assertThrows(AlreadyTakenException.class, () -> {
            game.updateGame(gameID, "BLACK", "another user");
        });
    }
}