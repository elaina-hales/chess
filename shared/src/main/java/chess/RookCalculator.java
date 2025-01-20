package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookCalculator {
    public RookCalculator(){
    }

    public static Collection<ChessMove> getMoves(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type, ChessPosition myPosition, ChessBoard board) {
        Collection<ChessMove> moves = new ArrayList<>();
        int myCol = myPosition.getColumn();
        int myRow = myPosition.getRow();
        for (int i = 1; i < 9; i++){
            ChessPosition endPos = new ChessPosition(i, myCol);
            if ((board.getPiece(endPos) != null ) && (board.getPiece(endPos).getTeamColor() != pieceColor)){
                ChessMove cur = new ChessMove(myPosition, endPos, null);
                moves.add(cur);
            } else if (board.getPiece(endPos) == null){
                ChessMove cur = new ChessMove(myPosition, endPos, null);
                moves.add(cur);
            }
        }


        for (int i = 1; i < 9; i++){
            ChessPosition endPos = new ChessPosition(myRow, i);
            if ((board.getPiece(endPos) != null ) && (board.getPiece(endPos).getTeamColor() != pieceColor)){
                ChessMove cur = new ChessMove(myPosition, endPos, null);
                moves.add(cur);
            } else if (board.getPiece(endPos) == null){
                ChessMove cur = new ChessMove(myPosition, endPos, null);
                moves.add(cur);
            }
        }
        return moves;
    }

}
