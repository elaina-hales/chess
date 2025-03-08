package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import service.AlreadyTakenException;
import service.BadReqException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;


public class SQLGame implements GameDAO{
    @Override
    public Collection<GameData> listGames() {
        Collection<GameData> games = new ArrayList<>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM Game";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()){
                        int id = rs.getInt("GameID");
                        String whiteUser = rs.getString("WhiteUsername");
                        String blackUser = rs.getString("BlackUsername");
                        String gameName = rs.getString("GameName");
                        String chessGame = rs.getString("ChessGame");

                        ChessGame chessGameDes = new Gson().fromJson(chessGame, ChessGame.class);
                        GameData game = new GameData(id, whiteUser, blackUser, gameName, chessGameDes);
                        games.add(game);
                    }
                    return games;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameData getGame(int gameID) {
        GameData game = null;
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM Game WHERE GameID=?";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()){
                        int id = rs.getInt("GameID");
                        String whiteUser = rs.getString("WhiteUsername");
                        String blackUser = rs.getString("BlackUsername");
                        String gameName = rs.getString("GameName");
                        String chessGame = rs.getString("ChessGame");

                        ChessGame chessGameDes = new Gson().fromJson(chessGame, ChessGame.class);
                        game = new GameData(id, whiteUser, blackUser, gameName, chessGameDes);
                    }
                    return game;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int createGame(String gameName) {
        Random random = new Random();
        ChessGame game = new ChessGame();
        var json = new Gson().toJson(game);
        int gameID = random.nextInt(10000);
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO Game (GameID, WhiteUsername, BlackUsername, GameName, ChessGame) VALUES (?, ?, ?, ?, ?)";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setInt(1, gameID);
                preparedStatement.setString(2, null);
                preparedStatement.setString(3, null);
                preparedStatement.setString(4, gameName);
                preparedStatement.setString(5, json);

                preparedStatement.executeUpdate();
                return gameID;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateGame(int gameID, String color, String username) throws AlreadyTakenException, BadReqException {
        var statement = "";
        try (var conn = DatabaseManager.getConnection()) {
            if (color.equals("WHITE")) {
                statement = "UPDATE Game SET WhiteUsername=? WHERE GameID=?";
            } else if (color.equals("BLACK")) {
                statement = "UPDATE Game SET BlackUsername=? WHERE GameID=?";
            }
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, username);

                preparedStatement.executeUpdate();} catch (Exception e) {
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
            GameData game = getGame(gameID);
            if (color.equals("BLACK")) {
                if (game.blackUsername() == null) {
                    String wUser = game.whiteUsername();
                    String gameName = game.gameName();
                    GameData updatedGame = new GameData(gameID, wUser, username, gameName, game.game());
                    games.add(updatedGame);
                    games.remove(game);
                } else {
                    throw new AlreadyTakenException("Error: already taken");
                }
            } else if (color.equals("WHITE")) {
                if (game.whiteUsername() == null) {
                    String bUser = game.blackUsername();
                    String gameName = game.gameName();
                    GameData updatedGame = new GameData(gameID, username, bUser, gameName, game.game());
                    games.add(updatedGame);
                    games.remove(game);
                } else {
                    throw new AlreadyTakenException("Error: already taken");
                }
            } else {
                throw new BadReqException("Error: bad request");
            }
        }
    }

    @Override
    public void clear(){
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "TRUNCATE Game";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
