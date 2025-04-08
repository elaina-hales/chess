package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.*;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.UnauthorizedException;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;


@WebSocket
public class WebSocketHandler {
    private final UserDAO user = new SQLUser();
    private final AuthDAO auth = new SQLAuth();
    private final GameDAO game = new SQLGame();
    private final Gson g = new Gson();

    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String msg) throws IOException {
        try {
            System.out.println(msg);
            UserGameCommand command = g.fromJson(msg, UserGameCommand.class);
            if (command.getCommandType().equals(UserGameCommand.CommandType.MAKE_MOVE)){
                UserGameCommand command = g.fromJson(msg, UserGameCommand.class);
            }
            String username = auth.getAuth(command.getAuthToken());

            connections.add(command.getGameID(), username, session);

            if (username == null){
                var errorNotification = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                errorNotification.addErrorMessage("Error: Unauthorized.");
                connections.sendErrorToOneUser(session, errorNotification);
                return;
            }


            switch (command.getCommandType()) {
                case CONNECT -> connect(username, command);
                case MAKE_MOVE -> makeMove(username, command);
                case LEAVE -> leaveGame(session, username, command);
                case RESIGN -> resign(session, username, command);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            var errorNotification = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            errorNotification.addErrorMessage("Error: " + ex.getMessage());
            connections.sendErrorToOneUser(session, errorNotification);
        }
    }

    private void connect(String username, UserGameCommand command) throws IOException {
        var rootNotification = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        GameData resp = game.getGame(command.getGameID());
        if (resp == null) {
            var errorNotification = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            errorNotification.addErrorMessage("Error: Invalid gameID.");
            connections.sendBackToRootClient(command.getGameID(), username, errorNotification);
            return;
        }
        ChessGame currentGame = resp.game();
        rootNotification.addGame(currentGame);
        connections.sendBackToRootClient(command.getGameID(), username, rootNotification);

        var allElseNotification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        var message = String.format("%s joined the game.\n", username);
        allElseNotification.addMessage(message);
        connections.broadcast(command.getGameID(), username, allElseNotification);
    }

    private void makeMove(String username, UserGameCommand command) {
        var rootNotification = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        GameData resp = game.getGame(command.getGameID());
        if (resp == null) {
            var errorNotification = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            errorNotification.addErrorMessage("Error: Invalid gameID.");
//            connections.sendBackToRootClient(command.getGameID(), username, errorNotification);
            return;
        }
        ChessGame currentGame = resp.game();
        rootNotification.addGame(currentGame);

    }

    private void leaveGame(Session session, String username, UserGameCommand command) {

    }

    private void resign(Session session, String username, UserGameCommand command) {

    }


//    private void enter(String visitorName, Session session) throws IOException {
//        connections.add(visitorName, session);
//        var message = String.format("%s is in the shop", visitorName);
//        var notification = new ServerMessage(ServerMessage.serverMessageType, message);
//        connections.broadcast(visitorName, notification);
//    }
//
//    private void exit(String visitorName) throws IOException {
//        connections.remove(visitorName);
//        var message = String.format("%s left the shop", visitorName);
//        var notification = new ServerMessage(ServerMessage.Type.DEPARTURE, message);
//        connections.broadcast(visitorName, notification);
//    }
//
//    public void makeNoise(String petName, String sound) throws ResponseException {
//        try {
//            var message = String.format("%s says %s", petName, sound);
//            var notification = new ServerMessage(ServerMessage.Type.NOISE, message);
//            connections.broadcast("", notification);
//        } catch (Exception ex) {
//            throw new ResponseException(500, ex.getMessage());
//        }
//    }
}