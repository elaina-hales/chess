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

        ChessBoard board = chess.getBoard();
        out.print(SET_BG_COLOR_DARK_GREY);
        String header;
        if (perspective.equals("white")){
            header = "    a  b  c  d  e  f  g  h \n";
            out.print(header);
            drawWhite(out, board);
        } else {
            header = "    h  g  f  e  d  c  b  a \n";
            out.print(header);
            drawBlack(out, board);
        }
        out.print(header);
        out.print(RESET_TEXT_COLOR);
        out.print(RESET_BG_COLOR);
        out.print(RESET_TEXT_BOLD_FAINT);
    }


    public void drawWhite(PrintStream out, ChessBoard board){
        int row;
        int column;
        for (int i = 1; i < 9; i++){
            row = 9 - i;
            out.print(" ");
            out.print(row);
            out.print(" ");
            for (int j = 1; j < 9; j++){
                column = j;
                drawRow(out, board, row, column, i, j);
            }
            out.print(SET_BG_COLOR_DARK_GREY);
            out.print(" ");
            out.print(row);
            out.print("\n");
        }
    }


    public void drawBlack(PrintStream out, ChessBoard board){
        int row;
        int column;
        for (int i = 1; i < 9; i++){
            row = i;
            out.print(" ");
            out.print(row);
            out.print(" ");
            for (int j = 1; j < 9; j++){
                column = 9 - j;
                drawRow(out, board, row, column, i, j);
            }
            out.print(SET_BG_COLOR_DARK_GREY);
            out.print(" ");
            out.print(row);
            out.print("\n");
        }
    }

    private void drawRow(PrintStream out, ChessBoard board, int row, int column, int i, int j) {
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


}
