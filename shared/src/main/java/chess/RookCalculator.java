package chess;

import chess.ChessGame.TeamColor;

import java.util.ArrayList;
import java.util.Collection;

public class RookCalculator implements PieceMovesCalculator {
    private final Collection<ChessMove> moves = new ArrayList<>();
    public RookCalculator(){
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


    public void forwardMoves(TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol){
        for (int i = myRow+1; i < 9; i++){
            ChessPosition endPos = new ChessPosition(i, myCol);
            if (moveHelper(endPos, teamColor, board, myPosition)){
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


    public void rightMoves(TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol){
        for (int i = myCol+1; i < 9; i++){
            ChessPosition endPos = new ChessPosition(myRow, i);
            if (moveHelper(endPos, teamColor, board, myPosition)){
                break;
            }
        }
    }

    public void leftMoves(TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol){
        for (int i = myCol-1; i >= 1; i--){
            ChessPosition endPos = new ChessPosition(myRow, i);
            if (moveHelper(endPos, teamColor, board, myPosition)){
                break;
            }
        }
    }

    @Override
    public Collection<ChessMove> getMoves(TeamColor teamColor, ChessBoard board, ChessPosition myPosition) {
        int myCol = myPosition.getColumn();
        int myRow = myPosition.getRow();
        forwardMoves(teamColor, board, myPosition, myRow, myCol);
        backwardMoves(teamColor, board, myPosition, myRow, myCol);
        rightMoves(teamColor, board, myPosition, myRow, myCol);
        leftMoves(teamColor, board, myPosition, myRow, myCol);
        return moves;
    }
}
