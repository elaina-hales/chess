package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnCalculator implements PieceMovesCalculator {
    public PawnCalculator(){
    }

    boolean canPromote(ChessGame.TeamColor teamColor, int myRow) {
        if ((myRow == 8) && teamColor.equals(ChessGame.TeamColor.WHITE)) {
            return true;
        } else if ((myRow == 1) && teamColor.equals(ChessGame.TeamColor.BLACK)) {
            return true;
        }
        return false;
    }


    public Collection<ChessMove> promote(ChessGame.TeamColor teamColor, ChessPosition myPosition, ChessPosition endPos, int myRow, Collection<ChessMove> moves){
        ChessMove q_promotion = new ChessMove(myPosition, endPos, ChessPiece.PieceType.QUEEN);
        ChessMove r_promotion = new ChessMove(myPosition, endPos, ChessPiece.PieceType.ROOK);
        ChessMove k_promotion = new ChessMove(myPosition, endPos, ChessPiece.PieceType.KNIGHT);
        ChessMove b_promotion = new ChessMove(myPosition, endPos, ChessPiece.PieceType.BISHOP);
        moves.add(q_promotion);
        moves.add(r_promotion);
        moves.add(k_promotion);
        moves.add(b_promotion);
        return moves;
    }

    public Collection<ChessMove> moveForwardOne(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        if ((myRow >= 1) && (myRow < 9)) {
            ChessPosition endPos = new ChessPosition(myRow, myCol);
            ChessPiece curPiece = board.getPiece(endPos);
            if (curPiece == null) {
                if (canPromote(teamColor, myRow)) {
                    moves = promote(teamColor, myPosition, endPos, myRow, moves);
                } else {
                    ChessMove cur = new ChessMove(myPosition, endPos, null);
                    moves.add(cur);
                }
            }
        }
        return moves;
    }

    public Collection<ChessMove> capture(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        int right = myCol+1;
        int left = myCol-1;
        if ((myRow >= 1) && (myRow < 9)) {
            if ((left >= 1)) {
                ChessPosition leftPos = new ChessPosition(myRow, left);
                ChessPiece leftPiece = board.getPiece(leftPos);
                if (leftPiece != null) {
                    if (!leftPiece.getTeamColor().equals(teamColor)) {
                        if (canPromote(teamColor, myRow)) {
                            promote(teamColor, myPosition, leftPos, myRow, moves);
                        } else {
                            ChessMove cur = new ChessMove(myPosition, leftPos, null);
                            moves.add(cur);
                        }
                    }
                }

            }
            if (right <= 8){
                ChessPosition rightPos = new ChessPosition(myRow, right);
                ChessPiece rightPiece = board.getPiece(rightPos);
                if (rightPiece != null) {
                    if (!rightPiece.getTeamColor().equals(teamColor)) {
                        if (canPromote(teamColor, myRow)) {
                            promote(teamColor, myPosition, rightPos, myRow, moves);
                        } else {
                            ChessMove cur = new ChessMove(myPosition, rightPos, null);
                            moves.add(cur);
                        }
                    }
                }
            }
        }
        return moves;
    }


    @Override
    public Collection<ChessMove> getMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int myCol = myPosition.getColumn();
        int myRow = myPosition.getRow();
        // forwards and backwards are different for different color pawns
        if (teamColor == ChessGame.TeamColor.WHITE){
            if (myRow == 2){
                ChessPosition curPos1 = new ChessPosition(myRow+2, myCol);
                ChessPosition curPos2 = new ChessPosition(myRow+1, myCol);
                if ((board.getPiece(curPos1) == null) && (board.getPiece(curPos2) == null)){
                    ChessMove cur = new ChessMove(myPosition, curPos1, null);
                    moves.add(cur);
                }
            }
            moves = moveForwardOne(teamColor, board, myPosition, myRow+1, myCol, moves);
            moves = capture(teamColor, board, myPosition, myRow+1, myCol, moves);
        } else if (teamColor == ChessGame.TeamColor.BLACK){
            if (myRow == 7){
                ChessPosition curPos1 = new ChessPosition(myRow-2, myCol);
                ChessPosition curPos2 = new ChessPosition(myRow-1, myCol);
                if ((board.getPiece(curPos1) == null) && (board.getPiece(curPos2) == null)){
                    ChessMove cur = new ChessMove(myPosition, curPos1, null);
                    moves.add(cur);
                }
            }
            moves = moveForwardOne(teamColor, board, myPosition, myRow-1, myCol, moves);
            moves = capture(teamColor, board, myPosition, myRow-1, myCol, moves);
        }
        return moves;
    }

}
