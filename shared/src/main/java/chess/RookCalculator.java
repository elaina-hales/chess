package chess;

import chess.ChessGame.TeamColor;
import java.util.Collection;

public class RookCalculator extends ParentCalculator implements PieceMovesCalculator {
    public RookCalculator(){
    }

    @Override
    public Collection<ChessMove> getMoves(TeamColor teamColor, ChessBoard board, ChessPosition myPosition) {
        int myCol = myPosition.getColumn();
        int myRow = myPosition.getRow();
        ParentCalculator calc = new ParentCalculator();
        calc.forwardMoves(teamColor, board, myPosition, myRow, myCol);
        calc.backwardMoves(teamColor, board, myPosition, myRow, myCol);
        calc.rtMoves(teamColor, board, myPosition, myRow, myCol);
        calc.ltMoves(teamColor, board, myPosition, myRow, myCol);
        return calc.getMoves();
    }
}
