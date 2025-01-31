package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopCalculator implements PieceMovesCalculator {
    public BishopCalculator(){
    }

    public boolean moveHelper(ChessPosition endPos, ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
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

    public void forwardRightMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        int tempCol = myCol+1;
        int tempRow = myRow+1;
        while ((tempCol < 9) && (tempRow < 9)) {
            ChessPosition endPos = new ChessPosition(tempRow, tempCol);
            if (moveHelper(endPos, teamColor, board, myPosition, myRow, myCol, moves)){
                break;
            }
            tempCol++;
            tempRow++;
        }
    }

    public void forwardLeftMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves) {
        int tempCol = myCol - 1;
        int tempRow = myRow + 1;
        while ((tempCol >= 1) && (tempRow < 9)) {
            ChessPosition endPos = new ChessPosition(tempRow, tempCol);
            if (moveHelper(endPos, teamColor, board, myPosition, myRow, myCol, moves)){
                break;
            }
            tempCol--;
            tempRow++;
        }
    }


    public void backLeftMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        int tempCol = myCol-1;
        int tempRow = myRow-1;
        while ((tempCol >= 1) && (tempRow >= 1)) {
            ChessPosition endPos = new ChessPosition(tempRow, tempCol);
            if (moveHelper(endPos, teamColor, board, myPosition, myRow, myCol, moves)){
                break;
            }
            tempCol--;
            tempRow--;
        }
    }


    public void backRightMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        int tempCol = myCol+1;
        int tempRow = myRow-1;
        while ((tempCol < 9) && (tempRow >= 1)) {
            ChessPosition endPos = new ChessPosition(tempRow, tempCol);
            if (moveHelper(endPos, teamColor, board, myPosition, myRow, myCol, moves)){
                break;
            }
            tempCol++;
            tempRow--;
        }
    }

   @Override
    public Collection<ChessMove> getMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int myCol = myPosition.getColumn();
        int myRow = myPosition.getRow();
        forwardRightMoves(teamColor, board, myPosition, myRow, myCol, moves);
        forwardLeftMoves(teamColor, board, myPosition, myRow, myCol, moves);
        backLeftMoves(teamColor, board, myPosition, myRow, myCol, moves);
        backRightMoves(teamColor, board, myPosition, myRow, myCol, moves);
        return moves;
    }

}
