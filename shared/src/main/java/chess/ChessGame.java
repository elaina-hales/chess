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
    private ChessGame.TeamColor teamTurn = ChessGame.TeamColor.WHITE;
    private boolean isOver = false;

    public ChessGame() {
        chessBoard.resetBoard();
    }

    public void setIsOver(boolean given) {
        isOver = given;
    }

    public boolean getIsOver(){
        return isOver;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
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
            Collection<ChessMove> tmpMoves = piece.pieceMoves(chessBoard, startPosition);
            for (ChessMove move : moves){
                ChessPiece originalPiece = chessBoard.getPiece(move.getEndPosition());
                chessBoard.addPiece(move.getEndPosition(), piece);
                chessBoard.addPiece(move.getStartPosition(), null);
                if (isInCheck(color)) {
                    tmpMoves.remove(move);
                }
                chessBoard.addPiece(move.getStartPosition(), piece);
                chessBoard.addPiece(move.getEndPosition(), originalPiece);
            }
            moves = tmpMoves;
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
        ChessPosition endPos = move.getEndPosition();
        ChessPiece piece = chessBoard.getPiece(startPos);
        Collection<ChessMove> validMoves = validMoves(startPos);
        if (piece != null && teamTurn == piece.getTeamColor()) {
            if (validMoves != null && !validMoves.isEmpty() && validMoves.contains(move)) {
                ChessPiece.PieceType promotionPiece = move.getPromotionPiece();
                if (promotionPiece == null) {
                    promotionPiece = piece.getPieceType();
                }
                ChessPiece newPiece = new ChessPiece(piece.getTeamColor(), promotionPiece);
                if (chessBoard.getPiece(endPos) == null) {
                    chessBoard.addPiece(endPos, newPiece);
                    chessBoard.addPiece(startPos, null);
                } else {
                    chessBoard.addPiece(endPos, null);
                    chessBoard.addPiece(endPos, newPiece);
                    chessBoard.addPiece(startPos, null);
                }
            } else {
                throw new InvalidMoveException();
            }
        } else {
            throw new InvalidMoveException();
        }
        if (teamTurn == TeamColor.WHITE) {
            setTeamTurn(TeamColor.BLACK);
        } else {
            setTeamTurn(TeamColor.WHITE);
        }
    }

    /**
     * Determines the king's position for a given team
     *
     * @param teamColor the color of the team
     * @return the position of the king
     */
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

    /**
     * Determines what moves from a list of moves are putting the king in check
     *
     * @param moves the list of moves
     * @param kingPos the king's position
     * @return a move
     */
    public ChessMove getMoveContainsKingPos(Collection<ChessMove> moves, ChessPosition kingPos){
        List<ChessMove> moveList = new ArrayList<>(moves);
        for (ChessMove move : moveList){
            if (move.getEndPosition().equals(kingPos)) {
                return move;
            }
        }
        return null;
    }

    /**
     * Determines what moves are putting the king in check
     *
     * @param teamColor which team to check for check
     * @param kingPosition the king's position
     * @return a list of moves that are putting the king in check
     */
    public Collection<ChessMove> getOffendingPieces(TeamColor teamColor, ChessPosition kingPosition){
        Collection<ChessMove> offensiveMoves = new ArrayList<>();
        for (int i = 1; i < 9; i++){
            for (int j = 1; j < 9; j++) {
                ChessPosition currentPosition = new ChessPosition(i,j);
                ChessPiece currentPiece = chessBoard.getPiece(currentPosition);
                if (currentPiece != null && currentPiece.getTeamColor() != teamColor){
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

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        // get the moves of all the pieces and see if the king is in those moves
        ChessPosition kingPosition = getKingPosition(teamColor);
        return !getOffendingPieces(teamColor, kingPosition).isEmpty();
    }


    /**
     * Determines if the given team has no moves
     *
     * @param teamColor which team to check for moves
     * @return True if the specified team has no moves
     */
    public boolean areNoMoves(TeamColor teamColor) {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition currentPosition = new ChessPosition(i, j);
                ChessPiece piece = chessBoard.getPiece(currentPosition);
                if ((piece != null) && (piece.getTeamColor() == teamColor)) {
                    if (!validMoves(currentPosition).isEmpty()) {
                        return false;
                    }
                }
            }
        }
        isOver = true;
        return true;
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
            return areNoMoves(teamColor);
        }
    }


    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (isInCheck(teamColor)) {
            return false;
        } else {
            return areNoMoves(teamColor);
        }
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
