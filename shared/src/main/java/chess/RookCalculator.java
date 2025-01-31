package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookCalculator implements PieceMovesCalculator {
    public RookCalculator(){
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


    public void forwardMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        for (int i = myRow+1; i < 9; i++){
            ChessPosition endPos = new ChessPosition(i, myCol);
            if (moveHelper(endPos, teamColor, board, myPosition, myRow, myCol, moves)){
                break;
            }
        }
    }


    public void backwardMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        for (int i = myRow-1; i >= 1 ; i--){
            ChessPosition endPos = new ChessPosition(i, myCol);
            if (moveHelper(endPos, teamColor, board, myPosition, myRow, myCol, moves)){
                break;
            }
        }
    }


    public void rightMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        for (int i = myCol+1; i < 9; i++){
            ChessPosition endPos = new ChessPosition(myRow, i);
            if (moveHelper(endPos, teamColor, board, myPosition, myRow, myCol, moves)){
                break;
            }
        }
    }

    public void leftMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        for (int i = myCol-1; i >= 1; i--){
            ChessPosition endPos = new ChessPosition(myRow, i);
            if (moveHelper(endPos, teamColor, board, myPosition, myRow, myCol, moves)){
                break;
            }
        }
    }

    @Override
    public Collection<ChessMove> getMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int myCol = myPosition.getColumn();
        int myRow = myPosition.getRow();
        forwardMoves(teamColor, board, myPosition, myRow, myCol, moves);
        backwardMoves(teamColor, board, myPosition, myRow, myCol, moves);
        rightMoves(teamColor, board, myPosition, myRow, myCol, moves);
        leftMoves(teamColor, board, myPosition, myRow, myCol, moves);
        return moves;
    }
}
