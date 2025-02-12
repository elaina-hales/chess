package chess;

import java.util.ArrayList;
import java.util.Collection;
import chess.ChessGame.TeamColor;


public class PawnCalculator implements PieceMovesCalculator {
    private final Collection<ChessMove> moves = new ArrayList<>();
    public PawnCalculator(){
    }

    public boolean canPromote(TeamColor teamColor, int myRow) {
        if ((myRow == 8) && teamColor.equals(TeamColor.WHITE)) {
            return true;
        } else if ((myRow == 1) && teamColor.equals(TeamColor.BLACK)){
            return true;
        }
        return false;
    }


    public void promote(ChessPosition myPosition, ChessPosition endPos){
        ChessMove qPromotion = new ChessMove(myPosition, endPos, ChessPiece.PieceType.QUEEN);
        ChessMove rPromotion = new ChessMove(myPosition, endPos, ChessPiece.PieceType.ROOK);
        ChessMove kPromotion = new ChessMove(myPosition, endPos, ChessPiece.PieceType.KNIGHT);
        ChessMove bPromotion = new ChessMove(myPosition, endPos, ChessPiece.PieceType.BISHOP);
        moves.add(qPromotion);
        moves.add(rPromotion);
        moves.add(kPromotion);
        moves.add(bPromotion);
    }

    public void moveForwardOne(TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol) {
        if ((myRow >= 1) && (myRow < 9)) {
            ChessPosition endPos = new ChessPosition(myRow, myCol);
            ChessPiece curPiece = board.getPiece(endPos);
            if (curPiece == null) {
                if (canPromote(teamColor, myRow)) {
                    promote(myPosition, endPos);
                } else {
                    ChessMove cur = new ChessMove(myPosition, endPos, null);
                    moves.add(cur);
                }
            }
        }
    }

    public void capture(TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol) {
        int right = myCol+1;
        int left = myCol-1;
        if ((myRow >= 1) && (myRow < 9)) {
            if (left >= 1){
                addCaptureMoves(teamColor, board, myPosition, myRow, left);
            }
            if (right <= 8){
                addCaptureMoves(teamColor, board, myPosition, myRow, right);
            }
        }
    }

    private void addCaptureMoves(TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int side) {
        ChessPosition rightPos = new ChessPosition(myRow, side);
        ChessPiece rightPiece = board.getPiece(rightPos);
        if (rightPiece != null && !rightPiece.getTeamColor().equals(teamColor)) {
            if (canPromote(teamColor, myRow)) {
                promote(myPosition, rightPos);
            } else {
                ChessMove cur = new ChessMove(myPosition, rightPos, null);
                moves.add(cur);
            }
        }
    }


    @Override
    public Collection<ChessMove> getMoves(TeamColor teamColor, ChessBoard board, ChessPosition myPosition) {
        int myCol = myPosition.getColumn();
        int myRow = myPosition.getRow();
        if (teamColor == TeamColor.WHITE){
            if (myRow == 2){
                ChessPosition curPos1 = new ChessPosition(myRow+2, myCol);
                ChessPosition curPos2 = new ChessPosition(myRow+1, myCol);
                if ((board.getPiece(curPos1) == null) && (board.getPiece(curPos2) == null)){
                    ChessMove cur = new ChessMove(myPosition, curPos1, null);
                    moves.add(cur);
                }
            }
            moveForwardOne(teamColor, board, myPosition, myRow+1, myCol);
            capture(teamColor, board, myPosition, myRow+1, myCol);
        } else if (teamColor == TeamColor.BLACK){
            if (myRow == 7){
                ChessPosition curPos1 = new ChessPosition(myRow-2, myCol);
                ChessPosition curPos2 = new ChessPosition(myRow-1, myCol);
                if ((board.getPiece(curPos1) == null) && (board.getPiece(curPos2) == null)){
                    ChessMove cur = new ChessMove(myPosition, curPos1, null);
                    moves.add(cur);
                }
            }
            moveForwardOne(teamColor, board, myPosition, myRow-1, myCol);
            capture(teamColor, board, myPosition, myRow-1, myCol);
        }
        return moves;
    }

}
