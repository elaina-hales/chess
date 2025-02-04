package chess;

import java.util.ArrayList;
import java.util.Collection;
import chess.ChessGame.TeamColor;


public class QueenCalculator implements PieceMovesCalculator {
    Collection<ChessMove> moves = new ArrayList<>();
    public QueenCalculator(){
    }

    public boolean moveHelper(ChessPosition endPos, TeamColor teamColor, ChessBoard board, ChessPosition myPosition){
        ChessPiece curPiece = board.getPiece(endPos);
        if (curPiece != null) {
            if (curPiece.getTeamColor().equals(teamColor)) {
                return true;
            } else {
                ChessMove cur = new ChessMove(myPosition, endPos, null);
                moves.add(cur);
                return true;
            }
        } else {
            ChessMove cur = new ChessMove(myPosition, endPos, null);
            moves.add(cur);
        }
        return false;
    }

    public void forwardMoves(TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol) {
        for (int i = myRow + 1; i < 9; i++) {
            ChessPosition endPos = new ChessPosition(i, myCol);
            if (moveHelper(endPos, teamColor, board, myPosition)) {
                break;
            }
        }
    }


    public void backwardMoves(TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol){
        for (int i = myRow-1; i >= 1 ; i--){
            ChessPosition endPos = new ChessPosition(i, myCol);
            if (moveHelper(endPos, teamColor, board, myPosition)){
                break;
            }
        }
    }


    public void rtMoves(TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol){
        for (int i = myCol+1; i < 9; i++){
            ChessPosition endPos = new ChessPosition(myRow, i);
            if (moveHelper(endPos, teamColor, board, myPosition)){
                break;
            }
        }
    }

    public void ltMoves(TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol){
        for (int i = myCol-1; i >= 1; i--){
            ChessPosition endPos = new ChessPosition(myRow, i);
            if (moveHelper(endPos, teamColor, board, myPosition)){
                break;
            }
        }
    }

    public void forwardRtMoves(TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol){
        int tempCol = myCol+1;
        int tempRow = myRow+1;
        while ((tempCol < 9) && (tempRow < 9)) {
            ChessPosition endPos = new ChessPosition(tempRow, tempCol);
            if (moveHelper(endPos, teamColor, board, myPosition)){
                break;
            }
            tempCol++;
            tempRow++;
        }
    }

    public void forwardLtMoves(TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol) {
        int tempCol = myCol - 1;
        int tempRow = myRow + 1;
        while ((tempCol >= 1) && (tempRow < 9)) {
            ChessPosition endPos = new ChessPosition(tempRow, tempCol);
            if (moveHelper(endPos, teamColor, board, myPosition)){
                break;
            }
            tempCol--;
            tempRow++;
        }
    }


    public void backLtMoves(TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol){
        int tempCol = myCol-1;
        int tempRow = myRow-1;
        while ((tempCol >= 1) && (tempRow >= 1)) {
            ChessPosition endPos = new ChessPosition(tempRow, tempCol);
            if (moveHelper(endPos, teamColor, board, myPosition)){
                break;
            }
            tempCol--;
            tempRow--;
        }
    }


    public void backRtMoves(TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol){
        int tempCol = myCol+1;
        int tempRow = myRow-1;
        while ((tempCol < 9) && (tempRow >= 1)) {
            ChessPosition endPos = new ChessPosition(tempRow, tempCol);
            if (moveHelper(endPos, teamColor, board, myPosition)){
                break;
            }
            tempCol++;
            tempRow--;
        }
    }

    @Override
    public Collection<ChessMove> getMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition) {
        int myCol = myPosition.getColumn();
        int myRow = myPosition.getRow();
        forwardMoves(teamColor, board, myPosition, myRow, myCol);
        backwardMoves(teamColor, board, myPosition, myRow, myCol);
        rtMoves(teamColor, board, myPosition, myRow, myCol);
        ltMoves(teamColor, board, myPosition, myRow, myCol);
        forwardLtMoves(teamColor, board, myPosition, myRow, myCol);
        forwardRtMoves(teamColor, board, myPosition, myRow, myCol);
        backLtMoves(teamColor, board, myPosition, myRow, myCol);
        backRtMoves(teamColor, board, myPosition, myRow, myCol);
        return moves;
    }

}
