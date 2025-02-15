package chess;

import java.util.ArrayList;
import java.util.Collection;

public class ParentCalculator{
    Collection<ChessMove> moves = new ArrayList<>();
    public ParentCalculator(){

    }
    public Collection<ChessMove> getMoves(){
        return moves;
    }

    public void movesHelp(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol){
        if ((myCol >= 1) && (myCol < 9) && (myRow >= 1) && (myRow < 9)) {
            ChessPosition endPos = new ChessPosition(myRow, myCol);
            ChessPiece curPiece = board.getPiece(endPos);
            if (curPiece != null) {
                if (!curPiece.getTeamColor().equals(teamColor)) {
                    ChessMove cur = new ChessMove(myPosition, endPos, null);
                    moves.add(cur);
                }
            } else {
                ChessMove cur = new ChessMove(myPosition, endPos, null);
                moves.add(cur);
            }
        }
    }

    public boolean isMove(ChessPosition endPos, ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition){
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

    public void forwardMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol) {
        for (int i = myRow + 1; i < 9; i++) {
            ChessPosition endPos = new ChessPosition(i, myCol);
            if (isMove(endPos, teamColor, board, myPosition)) {
                break;
            }
        }
    }


    public void backwardMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol){
        for (int i = myRow-1; i >= 1 ; i--){
            ChessPosition endPos = new ChessPosition(i, myCol);
            if (isMove(endPos, teamColor, board, myPosition)){
                break;
            }
        }
    }


    public void rtMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol){
        for (int i = myCol+1; i < 9; i++){
            ChessPosition endPos = new ChessPosition(myRow, i);
            if (isMove(endPos, teamColor, board, myPosition)){
                break;
            }
        }
    }

    public void ltMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol){
        for (int i = myCol-1; i >= 1; i--){
            ChessPosition endPos = new ChessPosition(myRow, i);
            if (isMove(endPos, teamColor, board, myPosition)){
                break;
            }
        }
    }

    public void forwardRtMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol){
        int tempCol = myCol+1;
        int tempRow = myRow+1;
        while ((tempCol < 9) && (tempRow < 9)) {
            ChessPosition endPos = new ChessPosition(tempRow, tempCol);
            if (isMove(endPos, teamColor, board, myPosition)){
                break;
            }
            tempCol++;
            tempRow++;
        }
    }

    public void forwardLtMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol) {
        int tempCol = myCol - 1;
        int tempRow = myRow + 1;
        while ((tempCol >= 1) && (tempRow < 9)) {
            ChessPosition endPos = new ChessPosition(tempRow, tempCol);
            if (isMove(endPos, teamColor, board, myPosition)){
                break;
            }
            tempCol--;
            tempRow++;
        }
    }


    public void backLtMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol){
        int tempCol = myCol-1;
        int tempRow = myRow-1;
        while ((tempCol >= 1) && (tempRow >= 1)) {
            ChessPosition endPos = new ChessPosition(tempRow, tempCol);
            if (isMove(endPos, teamColor, board, myPosition)){
                break;
            }
            tempCol--;
            tempRow--;
        }
    }


    public void backRtMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol){
        int tempCol = myCol+1;
        int tempRow = myRow-1;
        while ((tempCol < 9) && (tempRow >= 1)) {
            ChessPosition endPos = new ChessPosition(tempRow, tempCol);
            if (isMove(endPos, teamColor, board, myPosition)){
                break;
            }
            tempCol++;
            tempRow--;
        }
    }
}
