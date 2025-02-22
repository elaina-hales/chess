package service;
import model.UserData;
import requestsresults.*;
import dataaccess.*;

public class UserService {
    public RegisterResult register(RegisterRequest registerRequest, UserDAO user, AuthDAO userAuth) throws AlreadyTakenException, BadRequestException {
        UserData userResult = user.getUser(registerRequest.username());
        if (userResult == null){
            if (registerRequest.username() != null && registerRequest.password() != null && registerRequest.email() != null){
                UserData userData = new UserData(registerRequest.username(), registerRequest.password(), registerRequest.email());
                user.createUser(userData);
                String authToken = userAuth.createAuth(registerRequest.username());
                return new RegisterResult(registerRequest.username(), authToken);
            } else {
                throw new BadRequestException("Error: bad request");
            }
        } else {
            throw new AlreadyTakenException("Error: already taken");
        }
    }

    public LoginResult login(LoginRequest loginRequest, UserDAO user, AuthDAO userAuth) throws UnauthorizedException {
        String username = loginRequest.username();
        String password = loginRequest.password();
        UserData userResult = user.getUser(loginRequest.username());
        if (userResult != null){
            if (username != null && password != null && user.passMatch(username, password)){
                String authToken = userAuth.createAuth(username);
                return new LoginResult(username, authToken);
            }
        }
        throw new UnauthorizedException("Error: unauthorized");
    }

    public LogoutResult logout(LogoutRequest logoutRequest, AuthDAO userAuth) throws UnauthorizedException {
        String authToken = logoutRequest.authToken();
        String username = userAuth.getAuth(authToken);
        if (username == null){
            throw new UnauthorizedException("Error: unauthorized");
        } else {
            userAuth.deleteAuth(authToken);
            return new LogoutResult();
        }
    }
}
