package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingCalculator implements PieceMovesCalculator {
    public KingCalculator(){
    }

    public Collection<ChessMove> forwardMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        for (int i = myRow+1; i < 9; i++){
            ChessPosition endPos = new ChessPosition(i, myCol);
            ChessPiece curPiece = board.getPiece(endPos);
            if (curPiece != null) {
                if (curPiece.getTeamColor().equals(teamColor)) {
                    return moves;
                } else {
                    ChessMove cur = new ChessMove(myPosition, endPos, null);
                    moves.add(cur);
                    return moves;
                }
            } else {
                ChessMove cur = new ChessMove(myPosition, endPos, null);
                moves.add(cur);
            }
        }
        return moves;
    }


    public Collection<ChessMove> backwardMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        for (int i = myRow-1; i >= 1 ; i--){
            ChessPosition endPos = new ChessPosition(i, myCol);
            ChessPiece curPiece = board.getPiece(endPos);
            if (curPiece != null) {
                if (curPiece.getTeamColor().equals(teamColor)) {
                    return moves;
                } else {
                    ChessMove cur = new ChessMove(myPosition, endPos, null);
                    moves.add(cur);
                    return moves;
                }
            } else {
                ChessMove cur = new ChessMove(myPosition, endPos, null);
                moves.add(cur);
            }
        }
        return moves;
    }


    public Collection<ChessMove> rightMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        for (int i = myCol+1; i < 9; i++){
            ChessPosition endPos = new ChessPosition(myRow, i);
            ChessPiece curPiece = board.getPiece(endPos);
            if (curPiece != null) {
                if (curPiece.getTeamColor().equals(teamColor)) {
                    return moves;
                } else {
                    ChessMove cur = new ChessMove(myPosition, endPos, null);
                    moves.add(cur);
                    return moves;
                }
            } else {
                ChessMove cur = new ChessMove(myPosition, endPos, null);
                moves.add(cur);
            }
        }
        return moves;
    }

    public Collection<ChessMove> leftMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        for (int i = myCol-1; i >= 1; i--){
            ChessPosition endPos = new ChessPosition(myRow, i);
            ChessPiece curPiece = board.getPiece(endPos);
            if (curPiece != null) {
                if (curPiece.getTeamColor().equals(teamColor)) {
                    return moves;
                } else {
                    ChessMove cur = new ChessMove(myPosition, endPos, null);
                    moves.add(cur);
                    return moves;
                }
            } else {
                ChessMove cur = new ChessMove(myPosition, endPos, null);
                moves.add(cur);
            }
        }
        return moves;
    }

    @Override
    public Collection<ChessMove> getMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int myCol = myPosition.getColumn();
        int myRow = myPosition.getRow();
        Collection<ChessMove> f_moves = forwardMoves(teamColor, board, myPosition, myRow, myCol, moves);
        Collection<ChessMove> b_moves = backwardMoves(teamColor, board, myPosition, myRow, myCol, f_moves);
        Collection<ChessMove> r_moves = rightMoves(teamColor, board, myPosition, myRow, myCol, b_moves);
        Collection<ChessMove> all_moves = leftMoves(teamColor, board, myPosition, myRow, myCol, r_moves);
        return all_moves;
    }

}
