package client;

public record ReturnCreateObject(int statusCode, String statusMessage, CreateResult body) {
}
