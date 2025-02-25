package service;

import dataaccess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestsresults.*;

class UserServiceTest {
    private UserService service;
    private UserDAO user;
    private AuthDAO auth;
    private RegisterRequest req;

    @BeforeEach
    void setUp() {
        service = new UserService();
        user = new MemoryUser();
        auth = new MemoryAuth();
        req = new RegisterRequest("test", "password", "test@gmail.com");
    }

    @Test
    void testRegisterSuccess() {
        Assertions.assertDoesNotThrow(() -> {
            service.register(req, user, auth);
        });
    }

    @Test
    void testRegisterAlreadyTaken() throws BadReqException, AlreadyTakenException {
        service.register(req, user, auth);

        AlreadyTakenException exception = Assertions.assertThrows(AlreadyTakenException.class, () -> {
            service.register(req, user, auth);
        });

        Assertions.assertEquals("Error: already taken", exception.getMessage());
    }

    @Test
    void testLoginSuccess() throws BadReqException, AlreadyTakenException {
        service.register(req, user, auth);

        LoginRequest loginReq = new LoginRequest("test", "password");

        Assertions.assertDoesNotThrow(() -> {
            service.login(loginReq, user, auth);
        });
    }

    @Test
    void testLoginUnauthorized() throws BadReqException, AlreadyTakenException {
        service.register(req, user, auth);

        LoginRequest loginReq = new LoginRequest("test", "idk");

        UnauthorizedException exception = Assertions.assertThrows(UnauthorizedException.class, () -> {
            service.login(loginReq, user, auth);
        });

        Assertions.assertEquals("Error: unauthorized", exception.getMessage());
    }

    @Test
    void testLogoutSuccess() throws BadReqException, AlreadyTakenException {
        RegisterResult res = service.register(req, user, auth);
        LogoutRequest logoutReq = new LogoutRequest(res.authToken());

        Assertions.assertDoesNotThrow(() -> {
            service.logout(logoutReq, auth);
        });
    }

    @Test
    void testLogoutUnauthorized() throws BadReqException, AlreadyTakenException {
        service.register(req, user, auth);
        LogoutRequest logoutReq = new LogoutRequest("akjdhak=sjkhdkaj-akjfkajnck");

        UnauthorizedException exception = Assertions.assertThrows(UnauthorizedException.class, () -> {
            service.logout(logoutReq, auth);
        });

        Assertions.assertEquals("Error: unauthorized", exception.getMessage());
    }
}