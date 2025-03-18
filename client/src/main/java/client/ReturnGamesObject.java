package client;

import requestsresults.GamesResult;

public record ReturnGamesObject(int statusCode, String statusMessage, GamesResult body) {
}
