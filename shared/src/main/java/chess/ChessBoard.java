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
        ChessPiece bRook = new ChessPiece(ChessGame.TeamColor.BLACK, ROOK);
        ChessPiece wRook = new ChessPiece(ChessGame.TeamColor.WHITE, ROOK);
        ChessPosition bRook1 = new ChessPosition(8,1);
        ChessPosition bRook2 = new ChessPosition(8,8);
        ChessPosition wRook1 = new ChessPosition(1,1);
        ChessPosition wRook2 = new ChessPosition(1,8);

        ChessPiece bKnight = new ChessPiece(ChessGame.TeamColor.BLACK, KNIGHT);
        ChessPiece wKnight = new ChessPiece(ChessGame.TeamColor.WHITE, KNIGHT);
        ChessPosition bKnight1 = new ChessPosition(8,2);
        ChessPosition bKnight2 = new ChessPosition(8,7);
        ChessPosition wKnight1 = new ChessPosition(1,2);
        ChessPosition wKnight2 = new ChessPosition(1,7);

        ChessPiece bBishop = new ChessPiece(ChessGame.TeamColor.BLACK, BISHOP);
        ChessPiece wBishop = new ChessPiece(ChessGame.TeamColor.WHITE, BISHOP);
        ChessPosition bBishop1 = new ChessPosition(8,3);
        ChessPosition bBishop2 = new ChessPosition(8,6);
        ChessPosition wBishop1 = new ChessPosition(1,3);
        ChessPosition wBishop2 = new ChessPosition(1,6);

        ChessPiece bQueen = new ChessPiece(ChessGame.TeamColor.BLACK, QUEEN);
        ChessPiece wQueen = new ChessPiece(ChessGame.TeamColor.WHITE, QUEEN);
        ChessPosition bQueen1 = new ChessPosition(8,4);
        ChessPosition wQueen1 = new ChessPosition(1,4);

        ChessPiece bKing = new ChessPiece(ChessGame.TeamColor.BLACK, KING);
        ChessPiece wKing = new ChessPiece(ChessGame.TeamColor.WHITE, KING);
        ChessPosition bKing1 = new ChessPosition(8,5);
        ChessPosition wKing1 = new ChessPosition(1,5);

        ChessPiece bPawn = new ChessPiece(ChessGame.TeamColor.BLACK, PAWN);
        ChessPiece wPawn = new ChessPiece(ChessGame.TeamColor.WHITE, PAWN);
        ChessPosition bPawn0 = new ChessPosition(7,1);
        ChessPosition bPawn1 = new ChessPosition(7,2);
        ChessPosition bPawn2 = new ChessPosition(7,3);
        ChessPosition bPawn3 = new ChessPosition(7,4);
        ChessPosition bPawn4 = new ChessPosition(7,5);
        ChessPosition bPawn5 = new ChessPosition(7,6);
        ChessPosition bPawn6 = new ChessPosition(7,7);
        ChessPosition bPawn7 = new ChessPosition(7,8);

        ChessPosition wPawn0 = new ChessPosition(2,1);
        ChessPosition wPawn1 = new ChessPosition(2,2);
        ChessPosition wPawn2 = new ChessPosition(2,3);
        ChessPosition wPawn3 = new ChessPosition(2,4);
        ChessPosition wPawn4 = new ChessPosition(2,5);
        ChessPosition wPawn5 = new ChessPosition(2,6);
        ChessPosition wPawn6 = new ChessPosition(2,7);
        ChessPosition wPawn7 = new ChessPosition(2,8);

        addPiece(bRook1, bRook);
        addPiece(wRook1, wRook);
        addPiece(bRook2, bRook);
        addPiece(wRook2, wRook);

        addPiece(bKnight1, bKnight);
        addPiece(wKnight1, wKnight);
        addPiece(bKnight2, bKnight);
        addPiece(wKnight2, wKnight);

        addPiece(bBishop1, bBishop);
        addPiece(wBishop1, wBishop);
        addPiece(bBishop2, bBishop);
        addPiece(wBishop2, wBishop);

        addPiece(bQueen1, bQueen);
        addPiece(wQueen1, wQueen);

        addPiece(bKing1, bKing);
        addPiece(wKing1, wKing);

        addPiece(bPawn0, bPawn);
        addPiece(bPawn1, bPawn);
        addPiece(bPawn2, bPawn);
        addPiece(bPawn3, bPawn);
        addPiece(bPawn4, bPawn);
        addPiece(bPawn5, bPawn);
        addPiece(bPawn6, bPawn);
        addPiece(bPawn7, bPawn);

        addPiece(wPawn0, wPawn);
        addPiece(wPawn1, wPawn);
        addPiece(wPawn2, wPawn);
        addPiece(wPawn3, wPawn);
        addPiece(wPawn4, wPawn);
        addPiece(wPawn5, wPawn);
        addPiece(wPawn6, wPawn);
        addPiece(wPawn7, wPawn);

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
                "squares=" + Arrays.deepToString(squares) +
                '}';
    }
}
