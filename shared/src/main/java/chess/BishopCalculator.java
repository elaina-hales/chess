package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopCalculator implements PieceMovesCalculator {
    public BishopCalculator(){
    }

    public Collection<ChessMove> forwardRightMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        int tempCol = myCol+1;
        int tempRow = myRow+1;
        while ((tempCol < 9) && (tempRow < 9)) {
            ChessPosition endPos = new ChessPosition(tempRow, tempCol);
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
            tempCol++;
            tempRow++;
        }
        return moves;
    }

    public Collection<ChessMove> forwardLeftMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves) {
        int tempCol = myCol - 1;
        int tempRow = myRow + 1;
        while ((tempCol >= 1) && (tempRow < 9)) {
            ChessPosition endPos = new ChessPosition(tempRow, tempCol);
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
            tempCol--;
            tempRow++;
        }
        return moves;
    }


    public Collection<ChessMove> backLeftMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        int tempCol = myCol-1;
        int tempRow = myRow-1;
        while ((tempCol >= 1) && (tempRow >= 1)) {
            ChessPosition endPos = new ChessPosition(tempRow, tempCol);
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
            tempCol--;
            tempRow--;
        }
        return moves;
    }


    public Collection<ChessMove> backRightMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        int tempCol = myCol+1;
        int tempRow = myRow-1;
        while ((tempCol < 9) && (tempRow >= 1)) {
            ChessPosition endPos = new ChessPosition(tempRow, tempCol);
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
            tempCol++;
            tempRow--;
        }
        return moves;
    }

   @Override
    public Collection<ChessMove> getMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int myCol = myPosition.getColumn();
        int myRow = myPosition.getRow();
        Collection<ChessMove> fr_moves = forwardRightMoves(teamColor, board, myPosition, myRow, myCol, moves);
        Collection<ChessMove> fl_moves = forwardLeftMoves(teamColor, board, myPosition, myRow, myCol, fr_moves);
        Collection<ChessMove> bl_moves = backLeftMoves(teamColor, board, myPosition, myRow, myCol, fl_moves);
        Collection<ChessMove> all_moves = backRightMoves(teamColor, board, myPosition, myRow, myCol, bl_moves);
        return all_moves;
    }

}
