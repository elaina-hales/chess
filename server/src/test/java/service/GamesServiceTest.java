package service;

import dataaccess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestsresults.*;

class GamesServiceTest {
    private GamesService gamesService;
    private GameDAO game;
    private UserDAO user;
    private AuthDAO auth;
    private String authToken;

    @BeforeEach
    void setUp() throws BadReqException, AlreadyTakenException {
        gamesService = new GamesService();
        game = new MemoryGame();
        user = new MemoryUser();
        auth = new MemoryAuth();

        UserService userService = new UserService();
        RegisterRequest req = new RegisterRequest("test", "pass", "test@gmail.com");
        RegisterResult res = userService.register(req, user, auth);
        authToken = res.authToken();
    }

    @Test
    void testListGamesSuccess() throws UnauthorizedException {
        game.createGame("test1");
        game.createGame("test2");
        GamesResult expected = new GamesResult(game.listGames());

        GameDAO tmpGame = new MemoryGame();
        CreateRequest create1 = new CreateRequest("test1");
        CreateRequest create2 = new CreateRequest("test2");
        gamesService.create(create1, authToken, auth, tmpGame);
        gamesService.create(create2, authToken, auth, tmpGame);
        GamesRequest req = new GamesRequest(authToken);
        GamesResult actual = gamesService.listGames(req, auth, game);

        Assertions.assertEquals(expected, actual);
    }


    @Test
    void testListGamesUnauthorized() throws UnauthorizedException {
        CreateRequest create = new CreateRequest("test1");
        gamesService.create(create, authToken, auth, game);

        authToken = "akjhfkjha-ajfhk-askj";
        GamesRequest req = new GamesRequest(authToken);
        UnauthorizedException exception = Assertions.assertThrows(UnauthorizedException.class, () -> {
            gamesService.listGames(req, auth, game);
        });

        Assertions.assertEquals("Error: unauthorized", exception.getMessage());
    }


    @Test
    void testJoinSuccess() throws UnauthorizedException {
        CreateRequest create = new CreateRequest("test1");
        gamesService.create(create, authToken, auth, game);
        JoinRequest req = new JoinRequest("BLACK", 1);

        Assertions.assertDoesNotThrow(() -> {
            gamesService.join(req, authToken, auth, game);
        });
    }

    @Test
    void testJoinAlreadyTaken() throws UnauthorizedException, BadReqException, AlreadyTakenException {
        CreateRequest create = new CreateRequest("test1");
        gamesService.create(create, authToken, auth, game);
        String username = auth.getAuth(authToken);
        game.updateGame(1, "BLACK", username);

        JoinRequest req = new JoinRequest("BLACK", 1);
        AlreadyTakenException exception = Assertions.assertThrows(AlreadyTakenException.class, () -> {
            gamesService.join(req, authToken, auth, game);
        });

        Assertions.assertEquals("Error: already taken", exception.getMessage());
    }

    @Test
    void testCreateSuccess() throws UnauthorizedException {
        CreateRequest create = new CreateRequest("test1");

        Assertions.assertDoesNotThrow(() -> {
            gamesService.create(create, authToken, auth, game);
        });
    }

    @Test
    void testCreateUnauthorized() {
        CreateRequest create = new CreateRequest("test2");
        authToken = "invalid token!";
        UnauthorizedException exception = Assertions.assertThrows(UnauthorizedException.class, () -> {
            gamesService.create(create, authToken, auth, game);
        });

        Assertions.assertEquals("Error: unauthorized", exception.getMessage());

    }
}