package client;
import com.google.gson.Gson;
import model.GameData;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Collection;
import java.util.Map;


public class ServerFacade {
    private final String serverUrl;

    public ServerFacade(String url) {
        this.serverUrl = url;
    }

    private static HttpURLConnection sendRequest(String authToken, String url, String method, String body) throws URISyntaxException, IOException {
        URI uri = new URI(url);
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod(method);
        if (authToken != null){
            http.addRequestProperty("authorization", authToken);
        }
        writeRequestBody(body, http);
        http.connect();
        return http;
    }

    private static void writeRequestBody(String body, HttpURLConnection http) throws IOException {
        if (!body.isEmpty()) {
            http.setDoOutput(true);
            try (var outputStream = http.getOutputStream()) {
                outputStream.write(body.getBytes());
            }
        }
    }

    private static ReturnObject receiveResponse(HttpURLConnection http) throws IOException {
        var statusCode = http.getResponseCode();
        var statusMessage = http.getResponseMessage();
        Map<String, String> responseBody = readResponseBody(statusCode, http);
        ReturnObject r = new ReturnObject(statusCode, statusMessage, responseBody);
        return r;
    }

    private static ReturnGamesObject receiveResponseGames(HttpURLConnection http) throws IOException {
        var statusCode = http.getResponseCode();
        var statusMessage = http.getResponseMessage();
        Map<String, Collection<GameData>> responseBody = readRespListGames(statusCode, http);
        ReturnGamesObject r = new ReturnGamesObject(statusCode, statusMessage, responseBody);
        return r;
    }

    private static Map<String, String> readResponseBody(int statusCode, HttpURLConnection http) throws IOException {
        if (statusCode == 200){
            Map<String, String> responseBody;
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                responseBody = new Gson().fromJson(inputStreamReader, Map.class);
            }
            return responseBody;
        } else {
            return null;
        }
    }

    private static Map<String, Collection<GameData>> readRespListGames(int statusCode, HttpURLConnection http) throws IOException {
        if (statusCode == 200){
            Map<String, Collection<GameData>> responseBody;
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                responseBody = new Gson().fromJson(inputStreamReader, Map.class);
            }
            return responseBody;
        } else {
            return null;
        }
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
}

