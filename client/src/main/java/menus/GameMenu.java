package menus;

import chess.ChessGame;
import chess.ChessPosition;
import client.ServerFacade;
import repl.GameState;
import ui.DrawChessBoard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class GameMenu {
    public static GameState joined = GameState.JOINED_GAME;
    public static String username = "";
    public static String color = "";
    private static Map<Character, Integer> colMap = new HashMap<>();


    public GameMenu() {
    }

    public static String eval(String input, ServerFacade server, String givenUsername) {
        colMap.put('a', 1);
        colMap.put('b', 2);
        colMap.put('c', 3);
        colMap.put('d', 4);
        colMap.put('e', 5);
        colMap.put('f', 6);
        colMap.put('g', 7);
        colMap.put('h', 8);

        try {
            username = givenUsername;
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "redraw" -> redraw();
                case "leave" -> leave();
                case "move" -> null;
                case "resign" -> null;
                case "highlight" -> highlight(params[0]);
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

    public static String redraw() {
        DrawChessBoard d = new DrawChessBoard();
        d.draw(new ChessGame(), color, false);
        return "";
    }

    public static String highlight(String input) {
        if (!Pattern.matches("[a-h][1-8]", input)) {
            return "Invalid input. Input must be a lowercase letter (a-h) followed by a number (1-8).";
        }
        Character e = input.charAt(0);
        int row = Character.getNumericValue(input.charAt(1));
        int col = colMap.get(e);
        ChessPosition startPosition = new ChessPosition(row, col);
        DrawChessBoard d = new DrawChessBoard();
        d.highlight(new ChessGame(), color, startPosition);
        return "";
    }

    public static String help() {
        return """
                    redraw - board
                    leave - the game
                    move <start> <end> - make a move
                    resign - forfeit and leave the game
                    highlight <position> - highlight possible moves for a piece given its start position
                    help - possible commands
                    quit - exit chess
                """;
    }
}
