package client;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Map;


public class ServerFacade {
    private final String serverUrl;

    public ServerFacade(String url) {
        this.serverUrl = url;
    }

    private static HttpURLConnection sendRequest(String url, String method, String body) throws URISyntaxException, IOException {
        URI uri = new URI(url);
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod(method);
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
        ReturnObject r = new ReturnObject(statusCode, statusMessage);
        return r;
    }

    public ReturnObject login(String username, String password) throws IOException, URISyntaxException {
        String url = serverUrl + "session";
        String method = "POST";
        var body = Map.of("username", username, "password", password);
        var jsonBody = new Gson().toJson(body);
        HttpURLConnection http = sendRequest(url, method, jsonBody);
        return receiveResponse(http);
    }

    public ReturnObject register(String username, String password, String email) throws IOException, URISyntaxException {
        String url = serverUrl + "user";
        String method = "POST";
        var body = Map.of("username", username, "password", password, "email", email);
        var jsonBody = new Gson().toJson(body);
        HttpURLConnection http = sendRequest(url, method, jsonBody);
        return receiveResponse(http);
    }
}
