package client;

import com.google.gson.Gson;
import requestsresults.GamesResult;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class httpCommunicator {
    public static HttpURLConnection sendRequest(String authToken, String url, String method, String body) throws URISyntaxException, IOException {
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

    public static void writeRequestBody(String body, HttpURLConnection http) throws IOException {
        if (!body.isEmpty()) {
            http.setDoOutput(true);
            try (var outputStream = http.getOutputStream()) {
                outputStream.write(body.getBytes());
            }
        }
    }

    public static ReturnObject receiveResponse(HttpURLConnection http) throws IOException {
        var statusCode = http.getResponseCode();
        var statusMessage = http.getResponseMessage();
        Map<String, String> responseBody = readResponseBody(statusCode, http);
        ReturnObject r = new ReturnObject(statusCode, statusMessage, responseBody);
        return r;
    }

    public static ReturnGamesObject receiveResponseGames(HttpURLConnection http) throws IOException {
        var statusCode = http.getResponseCode();
        var statusMessage = http.getResponseMessage();
        GamesResult responseBody = readResponseListGames(statusCode, http);
        ReturnGamesObject r = new ReturnGamesObject(statusCode, statusMessage, responseBody);
        return r;
    }

    public static Map<String, String> readResponseBody(int statusCode, HttpURLConnection http) throws IOException {
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

    public static GamesResult readResponseListGames(int statusCode, HttpURLConnection http) throws IOException {
        if (statusCode == 200){
            GamesResult responseBody;
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                responseBody = new Gson().fromJson(inputStreamReader, GamesResult.class);
            }
            return responseBody;
        } else {
            return null;
        }
    }
}
