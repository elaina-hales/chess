package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard chessBoard;
    private boolean whiteTeamTurn = true;
    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        if (whiteTeamTurn){
            whiteTeamTurn = false;
            return TeamColor.WHITE;
        } else {
            whiteTeamTurn = true;
            return TeamColor.BLACK;
        }
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        if (team == TeamColor.WHITE){
            whiteTeamTurn = true;
        } else {
            whiteTeamTurn = false;
        }
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = chessBoard.getPiece(startPosition);
        Collection<ChessMove> moves = new ArrayList<>();
        if (isInCheckmate(piece.getTeamColor())){
            return moves;
        }
        return moves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        if (validMoves(move.getStartPosition()).contains(move)){
            // make the move
        }
    }

    public ChessPosition getKingPosition(TeamColor teamColor){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                ChessPosition currentPosition = new ChessPosition(i,j);
                ChessPiece currentPiece = chessBoard.getPiece(currentPosition);
                if ((currentPiece.getPieceType() == ChessPiece.PieceType.KING) && (currentPiece.getTeamColor() == teamColor)){
                    return currentPosition;
                }
            }
        }
        return new ChessPosition(0,0);
    }

    public boolean containsKingPos(Collection<ChessMove> moves, ChessPosition kingPos){
        for (ChessMove move : moves) {
            if (move.getEndPosition() == kingPos) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        // get the moves of all the pieces and see if the king is in those moves
        ChessPosition kingPosition = getKingPosition(teamColor);
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                ChessPosition currentPosition = new ChessPosition(i,j);
                ChessPiece currentPiece = chessBoard.getPiece(currentPosition);
                Collection<ChessMove> moves = currentPiece.pieceMoves(chessBoard, currentPosition);
                if (containsKingPos(moves, kingPosition)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */

    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) {
            return false;
        } else {
            ChessPosition kingPosition = getKingPosition(teamColor);
            ChessPiece king = chessBoard.getPiece(kingPosition);
            Collection<ChessMove> kingMoves = king.pieceMoves(chessBoard, kingPosition);
            // get offending pieces

            if (kingMoves.isEmpty()){
                if ()
            }
            return false;
        }
        // if the king is in danger, no move a piece could make to get the king out
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                ChessPosition currentPosition = new ChessPosition(i,j);
                ChessPiece currentPiece = chessBoard.getPiece(currentPosition);
                if (currentPiece.getTeamColor() == teamColor){
                    if (currentPiece.getPieceType() == ChessPiece.PieceType.KING){
                        if (isInCheck(teamColor)){
                            return false;
                        }
                    }
                    if (!currentPiece.pieceMoves(chessBoard, currentPosition).isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        chessBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return chessBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(chessBoard, chessGame.chessBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(chessBoard);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "chessBoard=" + chessBoard +
                '}';
    }
}
