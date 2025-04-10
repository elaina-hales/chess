package server.websocket;

import com.google.gson.Gson;
import handlers.ParentHandler;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<Integer, Vector<Connection>> connections = new ConcurrentHashMap<>();
    private final Gson gson = new Gson();

    public void add(int gameID, String username, Session session) {
        var connection = new Connection(username, session);
        connections.putIfAbsent(gameID, new Vector<Connection>());
        Vector<Connection> gameCnxns = connections.get(gameID);
        for (Connection cnxn: gameCnxns){
            if (cnxn.session == session) {
                return;
            }
        }
        gameCnxns.add(connection);
    }

    public void remove(int gameID, String visitorName) {
        Vector<Connection> gameCnxns = connections.get(gameID);
        for (Connection cnxn : gameCnxns) {
            if (cnxn.visitorName.equals(visitorName)) {
                connections.get(gameID).remove(cnxn);
            }
        }
    }

    public void sendErrorToOneUser(Session session, ServerMessage notification) throws IOException {
        for (var cnxns : connections.values()) {
            for (var cnxn : cnxns) {
                if (cnxn.session.isOpen() && (cnxn.session.equals(session))) {
                    cnxn.send(gson.toJson(notification));
                }
            }
        }
    }

    public void sendBackToRootClient(int gameID, String username, ServerMessage notification) throws IOException {
        Vector<Connection> gameCnxns = connections.get(gameID);
        for (var c : gameCnxns) {
            if (c.session.isOpen() && c.visitorName.equals(username)) {
                c.send(gson.toJson(notification));
            }
        }
    }

    public void broadcast(int gameID, String excludeVisitorName, ServerMessage notification) throws IOException {
        var removeList = new ArrayList<Connection>();
        Vector<Connection> gameCnxns = connections.get(gameID);
        for (var c : gameCnxns) {
            if (c.session.isOpen()) {
                if (!c.visitorName.equals(excludeVisitorName)) {
                    c.send(gson.toJson(notification));
                }
            } else {
                removeList.add(c);
            }
        }

        for (var c : removeList) {
            connections.remove(c.visitorName);
        }
    }


}