package chess;

import java.util.Collection;

public class QueenCalculator extends ParentCalculator implements PieceMovesCalculator {
    public QueenCalculator(){
    }

    @Override
    public Collection<ChessMove> getMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition) {
        int myCol = myPosition.getColumn();
        int myRow = myPosition.getRow();
        ParentCalculator calc = new ParentCalculator();
        calc.forwardMoves(teamColor, board, myPosition, myRow, myCol);
        calc.backwardMoves(teamColor, board, myPosition, myRow, myCol);
        calc.rtMoves(teamColor, board, myPosition, myRow, myCol);
        calc.ltMoves(teamColor, board, myPosition, myRow, myCol);
        calc.forwardLtMoves(teamColor, board, myPosition, myRow, myCol);
        calc.forwardRtMoves(teamColor, board, myPosition, myRow, myCol);
        calc.backLtMoves(teamColor, board, myPosition, myRow, myCol);
        calc.backRtMoves(teamColor, board, myPosition, myRow, myCol);
        return calc.getMoves();
    }

}
