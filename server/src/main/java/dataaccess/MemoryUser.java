package dataaccess;

import model.UserData;

import java.util.HashMap;
import java.util.Objects;


public class MemoryUser implements UserDAO{
    final private HashMap<String, UserData> users = new HashMap<>();

    @Override
    public void createUser(UserData user) {
        user = new UserData(user.username(), user.password(), user.email());
        users.put(user.username(), user);
    }

    @Override
    public UserData getUser(String username) {
        return users.get(username);
    }

    @Override
    public Boolean isPassMatch(String username, String password) {
        UserData user = getUser(username);
        return user.password().equals(password);
    }

    @Override
    public void clear(){
        users.clear();
    }
}
