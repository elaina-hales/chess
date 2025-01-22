package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightCalculator implements PieceMovesCalculator {
    public KnightCalculator(){
    }

    public Collection<ChessMove> movesHelp(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        if ((myCol >= 1) && (myCol < 9) && (myRow >= 1) && (myRow < 9)) {
            ChessPosition endPos = new ChessPosition(myRow, myCol);
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
        moves = movesHelp(teamColor, board, myPosition, myRow+2, myCol+1, moves);
        moves = movesHelp(teamColor, board, myPosition, myRow+2, myCol-1, moves);
        moves = movesHelp(teamColor, board, myPosition, myRow-2, myCol-1, moves);
        moves = movesHelp(teamColor, board, myPosition, myRow-2, myCol+1, moves);

        moves = movesHelp(teamColor, board, myPosition, myRow+1, myCol+2, moves);
        moves = movesHelp(teamColor, board, myPosition, myRow+1, myCol-2, moves);
        moves = movesHelp(teamColor, board, myPosition, myRow-1, myCol-2, moves);
        moves = movesHelp(teamColor, board, myPosition, myRow-1, myCol+2, moves);
        return moves;
    }

}
