package server.websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataaccess.*;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.AlreadyTakenException;
import service.BadReqException;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;


@WebSocket
public class WebSocketHandler {
    private final UserDAO user = new SQLUser();
    private final AuthDAO auth = new SQLAuth();
    private final GameDAO game = new SQLGame();
    private final Gson g = new Gson();
    private enum userType {
        OBSERVER,
        WHITE,
        BLACK
    };

    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String msg) throws IOException {
        try {
            UserGameCommand command = g.fromJson(msg, UserGameCommand.class);
            MakeMoveCommand m = null;
            if (command.getCommandType().equals(UserGameCommand.CommandType.MAKE_MOVE)) {
                m = g.fromJson(msg, MakeMoveCommand.class);
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
                case MAKE_MOVE -> makeMove(username, m);
                case LEAVE -> leaveGame(username, command);
                case RESIGN -> resign(username, command);
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

    private void makeMove(String username, MakeMoveCommand command) throws IOException {
        GameData resp = game.getGame(command.getGameID());
        ChessMove move = command.getMove();
        ChessGame chess = resp.game();
        // if the game is over
        if (chess.getIsOver()) {
            var errorNotification = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            errorNotification.addErrorMessage("The game is complete. No moves can be made.");
            connections.sendBackToRootClient(command.getGameID(), username, errorNotification);
            return;
        }

        // if the observer tries to make a move
        userType currentType;
        if (username.equals(resp.blackUsername())){
            currentType = userType.BLACK;
        } else if (username.equals(resp.whiteUsername())) {
            currentType = userType.WHITE;
        } else {
            var errorNotification = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            errorNotification.addErrorMessage("Observers cannot make moves.");
            connections.sendBackToRootClient(command.getGameID(), username, errorNotification);
            return;
        }

//         if the wrong team tries to make a move
        if (((currentType == userType.BLACK) && (chess.getTeamTurn() != ChessGame.TeamColor.BLACK)) ||
        ((currentType == userType.WHITE) && (chess.getTeamTurn() != ChessGame.TeamColor.WHITE))) {
            var errorNotification = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            errorNotification.addErrorMessage("It is not your turn to make a move.");
            connections.sendBackToRootClient(command.getGameID(), username, errorNotification);
            return;
        }

        try {
            chess.makeMove(move);
        } catch (InvalidMoveException e) {
            var errorNotification = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            errorNotification.addErrorMessage("Error: " + e.getMessage());
            connections.sendBackToRootClient(command.getGameID(), username, errorNotification);
            return;
        }

        game.updateGameNewMove(chess, command.getGameID());
        resp = game.getGame(command.getGameID());
        chess = resp.game();

        var allNotification = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        allNotification.addGame(chess);
        connections.broadcast(command.getGameID(), null, allNotification);

        var allElseNotification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        allElseNotification.addMessage(username + " made a move from "+ move.getStartPosition() + " to " + move.getEndPosition());
        connections.broadcast(command.getGameID(), username, allElseNotification);

        var checkNotification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        if (chess.isInCheckmate(ChessGame.TeamColor.BLACK)) {
            checkNotification.addMessage("Black is now in checkmate!");
            connections.broadcast(command.getGameID(), null, checkNotification);
            return;
        }
        if (chess.isInCheckmate(ChessGame.TeamColor.WHITE)) {
            checkNotification.addMessage("White is now in checkmate!");
            connections.broadcast(command.getGameID(), null, checkNotification);
            return;
        }
        if (chess.isInStalemate(ChessGame.TeamColor.BLACK)) {
            checkNotification.addMessage("White is now in stalemate!");
            connections.broadcast(command.getGameID(), null, checkNotification);
            return;
        }
        if (chess.isInStalemate(ChessGame.TeamColor.WHITE)) {
            checkNotification.addMessage("White is now in stalemate!");
            connections.broadcast(command.getGameID(), null, checkNotification);
            return;
        }
        if (chess.isInCheck(ChessGame.TeamColor.BLACK)) {
            checkNotification.addMessage("Black is now in check!");
            connections.broadcast(command.getGameID(), null, checkNotification);
        }
        if (chess.isInCheck(ChessGame.TeamColor.WHITE)) {
            checkNotification.addMessage("White is now in check!");
            connections.broadcast(command.getGameID(), null, checkNotification);
        }
    }

    private void leaveGame(String username, UserGameCommand command) throws IOException  {
        GameData resp = game.getGame(command.getGameID());

        if (username.equals(resp.blackUsername())){
            game.nullifyPlayer(command.getGameID(), "BLACK");
        } else if (username.equals(resp.whiteUsername())) {
            game.nullifyPlayer(command.getGameID(), "WHITE");
        }

        var allElseNotification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        var message = String.format("%s left the game.\n", username);
        allElseNotification.addMessage(message);
        connections.broadcast(command.getGameID(), username, allElseNotification);
        connections.remove(command.getGameID(), username);
    }

    private void resign(String username, UserGameCommand command) throws IOException {
        GameData resp = game.getGame(command.getGameID());
        ChessGame chess = resp.game();

        if (!username.equals(resp.whiteUsername()) && !username.equals(resp.blackUsername())) {
            var errorNotification = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            errorNotification.addErrorMessage("Observers cannot resign.");
            connections.sendBackToRootClient(command.getGameID(), username, errorNotification);
            return;
        }
        if (chess.getIsOver()) {
            var errorNotification = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            errorNotification.addErrorMessage("You cannot resign, the other team already resigned.");
            connections.sendBackToRootClient(command.getGameID(), username, errorNotification);
            return;
        }

        chess.setIsOver(true);
        game.updateGameNewMove(chess, command.getGameID());

        var allNotification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        var message = String.format("%s resigned. Game over.\n", username);
        allNotification.addMessage(message);

        connections.broadcast(command.getGameID(), null, allNotification);
        connections.remove(command.getGameID(), username);
    }
}