package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard chessBoard = new ChessBoard();
    private boolean whiteTeamTurn = true;
    public ChessGame() {
        chessBoard.resetBoard();
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
        if (piece != null){
            Collection<ChessMove> moves = piece.pieceMoves(chessBoard, startPosition);
            TeamColor color = piece.getTeamColor();
            for (ChessMove move : moves){
                if (!getOffendingPieces(color, move.getStartPosition()).isEmpty()){
                    moves = revisedKingMoves(color, moves, piece, startPosition);
                }
            }
            return moves;
        }
        return null;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPos = move.getStartPosition();
        Collection<ChessMove> validMoves = validMoves(startPos);
        if (validMoves.isEmpty()){
            throw new InvalidMoveException("Move not valid");
        } else if (validMoves.contains(move)){
            if(chessBoard.getPiece(move.getEndPosition()) == null){
                //chessBoard.addPiece();
            }

        }
    }

    public ChessPosition getKingPosition(TeamColor teamColor){
        for (int i = 1; i < 9; i++){
            for (int j = 1; j < 9; j++) {
                ChessPosition currentPosition = new ChessPosition(i,j);
                ChessPiece currentPiece = chessBoard.getPiece(currentPosition);
                if (currentPiece != null){
                    if ((currentPiece.getPieceType() == ChessPiece.PieceType.KING) && (currentPiece.getTeamColor() == teamColor)){
                        return currentPosition;
                    }
                }
            }
        }
        return null;
    }

    public ChessMove getMoveContainsKingPos(Collection<ChessMove> moves, ChessPosition kingPos){
        List<ChessMove> moveList = new ArrayList<>(moves);
        for (int i = 0; i < moveList.size(); i++){
            ChessMove move = moveList.get(i);
            if (move.getEndPosition().equals(kingPos)) {
                return move;
            }
        }
        return null;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public Collection<ChessMove> getOffendingPieces(TeamColor teamColor, ChessPosition kingPosition){
        Collection<ChessMove> offensiveMoves = new ArrayList<>();
        for (int i = 1; i < 9; i++){
            for (int j = 1; j < 9; j++) {
                ChessPosition currentPosition = new ChessPosition(i,j);
                ChessPiece currentPiece = chessBoard.getPiece(currentPosition);
                if (currentPiece != null){
                    Collection<ChessMove> moves = currentPiece.pieceMoves(chessBoard, currentPosition);
                    ChessMove offendingMove = getMoveContainsKingPos(moves, kingPosition);
                    if (offendingMove != null){
                        offensiveMoves.add(offendingMove);
                    }
                }
            }
        }
        return offensiveMoves;
    }

    public boolean isInCheck(TeamColor teamColor) {
        // get the moves of all the pieces and see if the king is in those moves
        ChessPosition kingPosition = getKingPosition(teamColor);
        if (getOffendingPieces(teamColor, kingPosition).isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */


    public Collection<ChessMove> revisedKingMoves(TeamColor teamColor, Collection<ChessMove> kingMoves, ChessPiece king, ChessPosition kingPosition){
        Collection<ChessMove> tmpMoves = king.pieceMoves(chessBoard, kingPosition);
        for (ChessMove move : kingMoves){
            Collection<ChessMove> offendingMoves = getOffendingPieces(teamColor, move.getEndPosition());
            for (ChessMove offensiveMove : offendingMoves) {
                if (move.getEndPosition().equals(offensiveMove.getEndPosition())) {
                    tmpMoves.remove(move);
                }
            }
        }
        return tmpMoves;
    }

    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) {
            return false;
        } else {
            // king is in danger and no valid moves
            ChessPosition kingPosition = getKingPosition(teamColor);
            ChessPiece king = chessBoard.getPiece(kingPosition);
            Collection<ChessMove> kingMoves = king.pieceMoves(chessBoard, kingPosition);
            if (revisedKingMoves(teamColor, kingMoves, king, kingPosition).isEmpty()) {
                for (int i = 1; i < 9; i++) {
                    for (int j = 1; j < 9; j++) {
                        ChessPosition currentPosition = new ChessPosition(i, j);
                        ChessPiece piece = chessBoard.getPiece(currentPosition);
                        if (piece != null) {
                            if (!validMoves(currentPosition).isEmpty()) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        ChessPosition kingPosition = getKingPosition(teamColor);
        if (isInCheck(teamColor)) {
            return false;
        } else {
            for (int i = 1; i < 9; i++) {
                for (int j = 1; j < 9; j++) {
                    ChessPosition currentPosition = new ChessPosition(i, j);
                    ChessPiece currentPiece = chessBoard.getPiece(currentPosition);
                    if (currentPiece != null && currentPiece.getTeamColor().equals(teamColor)) {
                        Collection<ChessMove> moves = currentPiece.pieceMoves(chessBoard, currentPosition);
                        if (currentPosition.equals(kingPosition)) {
                            if (!revisedKingMoves(teamColor, moves, currentPiece, kingPosition).isEmpty()){
                                return false;
                            }
                        } else if (!moves.isEmpty()) {
                            return false;
                        }
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
