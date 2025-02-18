package service;
import model.UserData;
import requestsresults.*;
import dataaccess.*;

public class UserService {
    public RegisterResult register(RegisterRequest registerRequest) throws AlreadyTakenException, BadRequestExeception {
        MemoryUser user = new MemoryUser();
        UserData result = user.getUser(registerRequest.username());
        if (result == null){
            if (registerRequest.username() != null && registerRequest.password() != null && registerRequest.email() != null){
                UserData userData = new UserData(registerRequest.username(), registerRequest.password(), registerRequest.email());
                user.createUser(userData);

                return
            } else {
                throw new BadRequestExeception();
            }
        } else {
            throw new AlreadyTakenException();
        }
    }
//    public LoginResult login(LoginRequest loginRequest) {}
//    public void logout(LogoutRequest logoutRequest) {}
}
