package menus;

import chess.ChessGame;
import consoleRepl.GameState;
import consoleRepl.State;
import model.GameData;
import ui.DrawChessBoard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class GameMenu {
    private static String username = null;
    public static State state = State.LOGGED_IN;
    public static GameState joined = GameState.JOINED_GAME;

    public GameMenu() {
    }

    public static String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "redraw" -> null;
                case "leave" -> leave();
                case "move" -> null;
                case "resign" -> null;
                case "highlight" -> null;
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public static String leave() {
        joined = GameState.NOT_JOINED;
        PostLogin.joined = GameState.NOT_JOINED;
        return "You have successfully left the game.\n";
    }

    public static String help() {
        return """
                    redraw - board
                    leave - the game
                    move <start> <end> - make a move
                    resign - forfeit and leave the game
                    highlight <piece> - highlight possible moves
                    quit - exit chess
                    help - possible commands
                """;
    }
}
