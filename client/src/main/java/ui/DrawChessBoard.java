package ui;

import chess.*;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class DrawChessBoard {
    public DrawChessBoard(){
    }

    public void draw(ChessGame chess, String perspective){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawRows(out, chess, perspective);
    }

    public void drawRows(PrintStream out, ChessGame chess, String perspective){
        ChessBoard board = chess.getBoard();
        out.print(SET_BG_COLOR_DARK_GREY);
        if (perspective.equals("white")){
            out.print("    a  b  c  d  e  f  g  h \n");
        } else {
            out.print("    h  g  f  e  d  c  b  a \n");
        }
        int row;
        int column;
        for (int i = 1; i < 9; i++){
            if (perspective.equals("white")) {
                row = 9 - i;
            } else {
                row = i;
            }
            out.print(" ");
            out.print(row);
            out.print(" ");
            for (int j = 1; j < 9; j++){
                if (perspective.equals("black")) {
                    column = 9 - j;
                } else {
                    column = j;
                }
                out.print(SET_TEXT_BOLD);
                if ((j + i) % 2 != 0){
                    out.print(SET_BG_COLOR_LIGHT_BROWN);
                } else {
                    out.print(SET_BG_COLOR_DARK_BROWN);
                }
                out.print(SET_TEXT_COLOR_WHITE);
                ChessPosition np = new ChessPosition(row, column);
                ChessPiece current = board.getPiece(np);
                String piece = "   ";
                if (current != null) {
                    String color = switch (current.getTeamColor()) {
                        case WHITE -> SET_TEXT_COLOR_WHITE;
                        case BLACK -> SET_TEXT_COLOR_BLACK;
                    };
                    piece = switch (current.getPieceType()) {
                        case ChessPiece.PieceType.ROOK -> " R ";
                        case ChessPiece.PieceType.KING -> " K ";
                        case ChessPiece.PieceType.QUEEN -> " Q ";
                        case ChessPiece.PieceType.KNIGHT -> " N ";
                        case ChessPiece.PieceType.BISHOP -> " B ";
                        case ChessPiece.PieceType.PAWN -> " P ";
                    };
                    out.print(color);
                }
                out.print(piece);
                out.print(RESET_TEXT_COLOR);
            }
            out.print(SET_BG_COLOR_DARK_GREY);
            out.print(" ");
            out.print(row);
            out.print("\n");
        }
        if (perspective.equals("white")){
            out.print("    a  b  c  d  e  f  g  h \n");
        } else {
            out.print("    h  g  f  e  d  c  b  a \n");
        }
    }







}
