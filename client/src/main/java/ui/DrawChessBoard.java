package ui;

import chess.*;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class DrawChessBoard {
    public DrawChessBoard(){
    }

    public void draw(ChessGame chess){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawRows(out, chess);
////        drawTicTacToeBoard(out);
//
//        out.print(SET_BG_COLOR_BLACK);
//        out.print(SET_TEXT_COLOR_WHITE);

    }

    public void drawRows(PrintStream out, ChessGame chess){
        ChessBoard board = chess.getBoard();
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print("    a  b  c  d  e  f  g  h \n");
        // for white perspective, invert outer loop
        // for black perspective, invert inner loop
        int outer = 1;
        int inner = 1;
        for (int i = 1; i < 9; i++){
            out.print(" ");
            out.print(i);
            out.print(" ");
            for (int j = 1; j < 9; j++){
                out.print(SET_TEXT_BOLD);
                if ((j + i) % 2 != 0){
                    out.print(SET_BG_COLOR_LIGHT_BROWN);
                } else {
                    out.print(SET_BG_COLOR_DARK_BROWN);
                }
                out.print(SET_TEXT_COLOR_WHITE);
                ChessPosition np = new ChessPosition(i, j);
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
            out.print(i);
            out.print("\n");
        }
        out.print("    a  b  c  d  e  f  g  h \n");
    }







}
