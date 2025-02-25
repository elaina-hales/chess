package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import requestsresults.*;

public class ClearService {
    public ClearResult clear(UserDAO user, AuthDAO userAuth, GameDAO games) {
        user.clear();
        userAuth.clear();
        games.clear();
        return new ClearResult();
    }
}
