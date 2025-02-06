package chess;

import java.util.ArrayList;
import java.util.Collection;
import chess.ChessGame.TeamColor;

public class KnightCalculator implements PieceMovesCalculator {
    private final Collection<ChessMove> moves = new ArrayList<>();
    public KnightCalculator(){
    }

    public void movesHelp(TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol) {
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

    @Override
    public Collection<ChessMove> getMoves(TeamColor teamColor, ChessBoard board, ChessPosition myPosition) {
        int myCol = myPosition.getColumn();
        int myRow = myPosition.getRow();
        movesHelp(teamColor, board, myPosition, myRow+2, myCol+1);
        movesHelp(teamColor, board, myPosition, myRow+2, myCol-1);
        movesHelp(teamColor, board, myPosition, myRow-2, myCol-1);
        movesHelp(teamColor, board, myPosition, myRow-2, myCol+1);

        movesHelp(teamColor, board, myPosition, myRow+1, myCol+2);
        movesHelp(teamColor, board, myPosition, myRow+1, myCol-2);
        movesHelp(teamColor, board, myPosition, myRow-1, myCol-2);
        movesHelp(teamColor, board, myPosition, myRow-1, myCol+2);
        return moves;
    }

}
