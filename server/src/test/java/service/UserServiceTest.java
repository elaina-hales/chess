package service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestsresults.RegisterRequest;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService service;

    @BeforeEach
    void setUp() {
        service = new UserService();
    }

    @Test
    void register() {
        RegisterRequest req = new RegisterRequest("test", "password", "test@gmail.com");

    }

    @Test
    void login() {
    }

    @Test
    void logout() {
    }
}