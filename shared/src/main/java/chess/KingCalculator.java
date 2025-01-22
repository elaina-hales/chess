package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingCalculator implements PieceMovesCalculator {
    public KingCalculator(){
    }

    public Collection<ChessMove> movesHelp(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        if ((myCol >= 1) && (myCol < 9) && (myRow >= 1) && (myRow < 9)) {
            ChessPosition endPos = new ChessPosition(myRow, myCol-1);
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
        Collection<ChessMove> moves1 = movesHelp(teamColor, board, myPosition, myRow+1, myCol, moves);
        Collection<ChessMove> moves2 = movesHelp(teamColor, board, myPosition, myRow-1, myCol, moves1);
        Collection<ChessMove> moves3 = movesHelp(teamColor, board, myPosition, myRow+1, myCol+1, moves2);
        Collection<ChessMove> moves4 = movesHelp(teamColor, board, myPosition, myRow-1, myCol-1, moves3);
        Collection<ChessMove> moves5 = movesHelp(teamColor, board, myPosition, myRow+1, myCol-1, moves4);
        Collection<ChessMove> moves6 = movesHelp(teamColor, board, myPosition, myRow-1, myCol+1, moves5);
        Collection<ChessMove> moves7 = movesHelp(teamColor, board, myPosition, myRow, myCol+1, moves6);
        Collection<ChessMove> all_moves = movesHelp(teamColor, board, myPosition, myRow, myCol-1, moves7);
        return all_moves;
    }

}
