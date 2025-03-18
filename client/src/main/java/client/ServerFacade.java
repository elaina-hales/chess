package client;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.Map;

import static client.HttpCommunicator.*;


public class ServerFacade {
    private final String serverUrl;

    public ServerFacade(String url) {
        this.serverUrl = url;
    }

    public ReturnObject login(String username, String password) throws IOException, URISyntaxException {
        String url = serverUrl + "session";
        String method = "POST";
        var body = Map.of("username", username, "password", password);
        var jsonBody = new Gson().toJson(body);
        HttpURLConnection http = sendRequest(null, url, method, jsonBody);

        return receiveResponse(http);
    }

    public ReturnObject register(String username, String password, String email) throws IOException, URISyntaxException {
        String url = serverUrl + "user";
        String method = "POST";
        var body = Map.of("username", username, "password", password, "email", email);
        var jsonBody = new Gson().toJson(body);
        HttpURLConnection http = sendRequest(null, url, method, jsonBody);
        return receiveResponse(http);
    }

    public ReturnObject logout(String authToken) throws IOException, URISyntaxException {
        String url = serverUrl + "session";
        String method = "DELETE";
        HttpURLConnection http = sendRequest(authToken, url, method, "");
        return receiveResponse(http);
    }

    public ReturnGamesObject listGames(String authToken) throws IOException, URISyntaxException {
        String url = serverUrl + "game";
        String method = "GET";
        HttpURLConnection http = sendRequest(authToken, url, method, "");
        return receiveResponseGames(http);
    }

    public ReturnObject create(String authToken, String gameName) throws IOException, URISyntaxException {
        String url = serverUrl + "game";
        String method = "POST";
        var body = Map.of("gameName", gameName);
        var jsonBody = new Gson().toJson(body);
        HttpURLConnection http = sendRequest(authToken, url, method, jsonBody);
        return receiveResponse(http);
    }

    public ReturnObject join(String authToken, int gameID, String color) throws IOException, URISyntaxException {
        String url = serverUrl + "game";
        String method = "PUT";
        var body = Map.of("playerColor", color.toUpperCase(), "gameID", gameID);
        var jsonBody = new Gson().toJson(body);
        HttpURLConnection http = sendRequest(authToken, url, method, jsonBody);
        return receiveResponse(http);
    }

    public ReturnObject clear() throws URISyntaxException, IOException {
        String url = serverUrl + "db";
        String method = "DELETE";
        HttpURLConnection http = sendRequest(null, url, method, "");
        return receiveResponse(http);
    }

}

