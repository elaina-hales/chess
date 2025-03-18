package client;

public record ReturnGamesObject(int statusCode, String statusMessage, GamesResult body) {
}
