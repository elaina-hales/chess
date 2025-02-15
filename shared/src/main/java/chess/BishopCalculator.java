package chess;

import java.util.Collection;
import chess.ChessGame.TeamColor;


public class BishopCalculator extends ParentCalculator implements PieceMovesCalculator {
    public BishopCalculator(){
    }

   @Override
    public Collection<ChessMove> getMoves(TeamColor teamColor, ChessBoard board, ChessPosition myPosition) {
        int myCol = myPosition.getColumn();
        int myRow = myPosition.getRow();
        ParentCalculator calc = new ParentCalculator();
        calc.forwardRtMoves(teamColor, board, myPosition, myRow, myCol);
        calc.forwardLtMoves(teamColor, board, myPosition, myRow, myCol);
        calc.backLtMoves(teamColor, board, myPosition, myRow, myCol);
        calc.backRtMoves(teamColor, board, myPosition, myRow, myCol);
        return calc.getMoves();
    }
}
