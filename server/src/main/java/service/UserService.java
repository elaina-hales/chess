package service;
import model.UserData;
import requestsresults.*;
import dataaccess.*;

public class UserService {
    public RegisterResult register(RegisterRequest registerRequest) throws AlreadyTakenException {
        UserData result = UserAccess.getUser(registerRequest.username());
        if (result == null){
            return

        } else {
            throw new AlreadyTakenException();
        }
    }
//    public LoginResult login(LoginRequest loginRequest) {}
//    public void logout(LogoutRequest logoutRequest) {}
}
