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

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    public void testLoginSuccess() throws IOException, URISyntaxException {
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


//    @Test
//    void register() throws Exception {
//        var authData = facade.register("player1", "password", "p1@email.com");
//        assertTrue(authData.authToken().length() > 10);
//    }

}
