package ui;

import chess.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import static ui.EscapeSequences.*;

public class DrawChessBoard {
    private final Collection<ChessPosition> endPositions = new ArrayList<>();
    private ChessPosition startPosition = null;

    public DrawChessBoard(){
    }

    public void highlight(ChessGame chess, String perspective, ChessPosition startPosition){
        Collection<ChessMove> moves = chess.validMoves(startPosition);
        this.startPosition = startPosition;
        for (ChessMove move: moves){
            ChessPosition pos = move.getEndPosition();
            endPositions.add(pos);
        }
        System.out.println(endPositions);
        draw(chess, perspective, true);
    }

    public void draw(ChessGame chess, String perspective, boolean isHighlight){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);

        ChessBoard board = chess.getBoard();
        out.print(SET_BG_COLOR_DARK_GREY);
        String header;
        if (perspective.equals("white")){
            header = "    a  b  c  d  e  f  g  h \n";
            out.print(header);
            drawWhite(out, board, isHighlight);
        } else {
            header = "    h  g  f  e  d  c  b  a \n";
            out.print(header);
            drawBlack(out, board, isHighlight);
        }
        out.print(header);
        out.print(RESET_TEXT_COLOR);
        out.print(RESET_BG_COLOR);
        out.print(RESET_TEXT_BOLD_FAINT);
    }


    public void drawWhite(PrintStream out, ChessBoard board, boolean isHighlight){
        int row;
        int column;
        for (int i = 1; i < 9; i++){
            row = 9 - i;
            out.print(" ");
            out.print(row);
            out.print(" ");
            for (int j = 1; j < 9; j++){
                column = j;
                if (isHighlight){
                    drawRowHighlight(out, board, row, column, i, j);
                } else {
                    drawRow(out, board, row, column, i, j);
                }
            }
            out.print(SET_BG_COLOR_DARK_GREY);
            out.print(" ");
            out.print(row);
            out.print("\n");
        }
    }


    public void drawBlack(PrintStream out, ChessBoard board, boolean isHighlight){
        int row;
        int column;
        for (int i = 1; i < 9; i++){
            row = i;
            out.print(" ");
            out.print(row);
            out.print(" ");
            for (int j = 1; j < 9; j++){
                column = 9 - j;
                if (isHighlight){
                    drawRowHighlight(out, board, row, column, i, j);
                } else {
                    drawRow(out, board, row, column, i, j);
                }
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
        writeSquare(out, current, false);
    }

    private void drawRowHighlight(PrintStream out, ChessBoard board, int row, int column, int i, int j) {
        out.print(SET_TEXT_BOLD);
        ChessPosition np = new ChessPosition(row, column);
        ChessPiece current = board.getPiece(np);
        if (np.equals(startPosition)){
            out.print(SET_TEXT_COLOR_DARK_GREY);
            out.print(SET_BG_COLOR_WHITE);
            writeSquare(out, current, true);
        } else {
            if (endPositions.contains(np)) {
                out.print(SET_TEXT_COLOR_DARK_GREY);
                if ((j + i) % 2 != 0){
                    out.print(SET_BG_COLOR_SOFT_DARK_GREEN);
                } else {
                    out.print(SET_BG_COLOR_SOFT_LIGHT_GREEN);
                }
                writeSquare(out, current, false);
            } else {
                if ((j + i) % 2 != 0){
                    out.print(SET_BG_COLOR_LIGHT_BROWN);
                } else {
                    out.print(SET_BG_COLOR_DARK_BROWN);
                }
                writeSquare(out, current, false);
            }
        }
    }

    private void writeSquare(PrintStream out, ChessPiece current, boolean isHighlight) {
        String piece = "   ";
        if (current != null) {
            String color;
            if (isHighlight){
                color = SET_TEXT_COLOR_RED;
            } else {
                color = switch (current.getTeamColor()) {
                    case WHITE -> SET_TEXT_COLOR_WHITE;
                    case BLACK -> SET_TEXT_COLOR_BLACK;
                };
            }
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
