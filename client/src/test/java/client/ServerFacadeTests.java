package client;

import org.junit.jupiter.api.*;
import server.Server;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:8080/");
    }

    @BeforeEach
    public void clear() throws URISyntaxException, IOException {
        facade.clear();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    void testRegisterSuccess() throws Exception {
        ReturnObject r = facade.register("test", "test", "p1@email.com");
        String authToken = r.body().get("authToken");
        assertTrue(authToken.length() > 10);
    }

    @Test
    void testRegisterAlreadyTaken() throws Exception {
        facade.register("test", "test", "p1@email.com");
        ReturnObject actual = facade.register("test", "test", "p1@email.com");
        assertTrue(actual.statusCode() == 403);
    }

    @Test
    public void testLoginSuccess() throws IOException, URISyntaxException {
        facade.register("test", "test", "p1@email.com");
        ReturnObject actual = facade.login("test", "test");
        assertTrue((actual.statusCode() == 200) && (actual.statusMessage().equals("OK") &&
                (actual.body().containsKey("authToken"))));

    }

    @Test
    public void testLoginUnauthorized() throws IOException, URISyntaxException {
        ReturnObject actual = facade.login("test", "not the right pw");
        assertTrue((actual.statusCode() == 401) &&
                (actual.statusMessage().equals("Unauthorized")));
    }

    @Test
    public void testLogoutSuccess() throws IOException, URISyntaxException {
        ReturnObject r = facade.register("test", "test", "p1@email.com");
        String authToken = r.body().get("authToken");
        ReturnObject actual = facade.logout(authToken);
        assertTrue(actual.statusCode() == 200);
    }

    @Test
    public void testLogoutUnauthorized() throws IOException, URISyntaxException {
        facade.register("test", "test", "p1@email.com");
        ReturnObject actual = facade.logout("not a valid auth");
        assertTrue((actual.statusCode() == 401) &&
                (actual.statusMessage().equals("Unauthorized")));
    }

    @Test
    public void testListSuccess() throws IOException, URISyntaxException {
        ReturnObject r = facade.register("test", "test", "p1@email.com");
        String authToken = r.body().get("authToken");
        ReturnGamesObject actual = facade.listGames(authToken);
        assertTrue(actual.statusCode() == 200);
    }

    @Test
    public void testListUnauthorized() throws IOException, URISyntaxException {
        facade.register("test", "test", "p1@email.com");
        ReturnGamesObject actual = facade.listGames("goober");
        assertTrue((actual.statusCode() == 401) &&
                (actual.statusMessage().equals("Unauthorized")));
    }

    @Test
    public void testCreateGameSuccess() throws IOException, URISyntaxException {
        ReturnObject r = facade.register("test", "test", "p1@email.com");
        String authToken = r.body().get("authToken");
        ReturnObject actual = facade.create(authToken, "testGame");
        assertTrue(actual.statusCode() == 200);
    }

    @Test
    public void testCreateGameUnauthorized() throws IOException, URISyntaxException {
        facade.register("test", "test", "p1@email.com");
        ReturnObject actual = facade.create("popopopop", "testGame");
        assertTrue(actual.statusCode() == 401);
    }






}
