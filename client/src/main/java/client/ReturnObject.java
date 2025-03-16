package client;

import java.util.Map;

public record ReturnObject(int statusCode, String statusMessage, Map<String, String> body) {
}
