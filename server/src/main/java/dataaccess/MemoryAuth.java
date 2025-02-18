package dataaccess;

import java.util.HashMap;
import java.util.UUID;


public class MemoryAuth implements AuthDAO{
    final private HashMap<String, String> usersAuth = new HashMap<>();
    private static String generateToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String createAuth(String username) {
        String authToken = generateToken();
        usersAuth.put(generateToken(), username);
        return authToken;
    }

    @Override
    public String getAuth(String authToken) {
        return usersAuth.get(authToken);
    }
}
