package client;

import model.GameData;

import java.util.Collection;
import java.util.Map;

public record ReturnGamesObject(int statusCode, String statusMessage, Map<String, Collection<GameData>> body) {
}
