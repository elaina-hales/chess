package requestsresults;

import model.GameData;

import java.util.Collection;

public record GamesResult(
        Collection<GameData> games){
}
