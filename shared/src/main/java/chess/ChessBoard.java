package chess;

import java.util.Arrays;
import java.util.Objects;

import static chess.ChessPiece.PieceType.*;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] squares = new ChessPiece[8][8];
    public ChessBoard() {
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        // set the whole board to null and then add in the pieces one by one
        for (int i = 1; i < 9; i++){
            for (int j = 1; j < 9; j++) {
                ChessPosition temp = new ChessPosition(i,j);
                addPiece(temp, null);
            }
        }
        ChessPiece b_rook = new ChessPiece(ChessGame.TeamColor.BLACK, ROOK);
        ChessPiece w_rook = new ChessPiece(ChessGame.TeamColor.WHITE, ROOK);
        ChessPosition b_rook1 = new ChessPosition(1,1);
        ChessPosition b_rook2 = new ChessPosition(1,8);
        ChessPosition w_rook1 = new ChessPosition(8,1);
        ChessPosition w_rook2 = new ChessPosition(8,8);

        ChessPiece b_knight = new ChessPiece(ChessGame.TeamColor.BLACK, KNIGHT);
        ChessPiece w_knight = new ChessPiece(ChessGame.TeamColor.WHITE, KNIGHT);
        ChessPosition b_knight1 = new ChessPosition(1,2);
        ChessPosition b_knight2 = new ChessPosition(1,7);
        ChessPosition w_knight1 = new ChessPosition(8,2);
        ChessPosition w_knight2 = new ChessPosition(8,7);

        ChessPiece b_bishop = new ChessPiece(ChessGame.TeamColor.BLACK, BISHOP);
        ChessPiece w_bishop = new ChessPiece(ChessGame.TeamColor.WHITE, BISHOP);
        ChessPosition b_bishop1 = new ChessPosition(1,3);
        ChessPosition b_bishop2 = new ChessPosition(1,6);
        ChessPosition w_bishop1 = new ChessPosition(8,3);
        ChessPosition w_bishop2 = new ChessPosition(8,6);

        ChessPiece b_queen = new ChessPiece(ChessGame.TeamColor.BLACK, QUEEN);
        ChessPiece w_queen = new ChessPiece(ChessGame.TeamColor.WHITE, QUEEN);
        ChessPosition b_queen1 = new ChessPosition(1,4);
        ChessPosition w_queen1 = new ChessPosition(8,4);

        ChessPiece b_king = new ChessPiece(ChessGame.TeamColor.BLACK, KING);
        ChessPiece w_king = new ChessPiece(ChessGame.TeamColor.WHITE, KING);
        ChessPosition b_king1 = new ChessPosition(1,5);
        ChessPosition w_king1 = new ChessPosition(8,5);

        ChessPiece b_pawn = new ChessPiece(ChessGame.TeamColor.BLACK, PAWN);
        ChessPiece w_pawn = new ChessPiece(ChessGame.TeamColor.WHITE, PAWN);
        ChessPosition b_pawn0 = new ChessPosition(2,1);
        ChessPosition b_pawn1 = new ChessPosition(2,2);
        ChessPosition b_pawn2 = new ChessPosition(2,3);
        ChessPosition b_pawn3 = new ChessPosition(2,4);
        ChessPosition b_pawn4 = new ChessPosition(2,5);
        ChessPosition b_pawn5 = new ChessPosition(2,6);
        ChessPosition b_pawn6 = new ChessPosition(2,7);
        ChessPosition b_pawn7 = new ChessPosition(2,8);

        ChessPosition w_pawn0 = new ChessPosition(7,1);
        ChessPosition w_pawn1 = new ChessPosition(7,2);
        ChessPosition w_pawn2 = new ChessPosition(7,3);
        ChessPosition w_pawn3 = new ChessPosition(7,4);
        ChessPosition w_pawn4 = new ChessPosition(7,5);
        ChessPosition w_pawn5 = new ChessPosition(7,6);
        ChessPosition w_pawn6 = new ChessPosition(7,7);
        ChessPosition w_pawn7 = new ChessPosition(7,8);

        addPiece(b_rook1, b_rook);
        addPiece(w_rook1, w_rook);
        addPiece(b_rook2, b_rook);
        addPiece(w_rook2, w_rook);

        addPiece(b_knight1, b_knight);
        addPiece(w_knight1, w_knight);
        addPiece(b_knight2, b_knight);
        addPiece(w_knight2, w_knight);

        addPiece(b_bishop1, b_bishop);
        addPiece(w_bishop1, w_bishop);
        addPiece(b_bishop2, b_bishop);
        addPiece(w_bishop2, w_bishop);

        addPiece(b_queen1, b_queen);
        addPiece(w_queen1, w_queen);

        addPiece(b_king1, b_king);
        addPiece(w_king1, w_king);

        addPiece(b_pawn0, b_pawn);
        addPiece(b_pawn1, b_pawn);
        addPiece(b_pawn2, b_pawn);
        addPiece(b_pawn3, b_pawn);
        addPiece(b_pawn4, b_pawn);
        addPiece(b_pawn5, b_pawn);
        addPiece(b_pawn6, b_pawn);
        addPiece(b_pawn7, b_pawn);

        addPiece(w_pawn0, w_pawn);
        addPiece(w_pawn1, w_pawn);
        addPiece(w_pawn2, w_pawn);
        addPiece(w_pawn3, w_pawn);
        addPiece(w_pawn4, w_pawn);
        addPiece(w_pawn5, w_pawn);
        addPiece(w_pawn6, w_pawn);
        addPiece(w_pawn7, w_pawn);

        for (int i = 1; i < 9; i++){
            for (int j = 1; j < 9; j++) {
                ChessPosition temp = new ChessPosition(i,j);
                System.out.print(i);
                System.out.print(j);
                System.out.println(getPiece(temp));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "squares=" + Arrays.toString(squares) +
                '}';
    }
}
