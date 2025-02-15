package chess;

import java.util.ArrayList;
import java.util.Collection;
import chess.ChessGame.TeamColor;

public class KnightCalculator extends ParentCalculator implements PieceMovesCalculator {
    public KnightCalculator(){
    }

    @Override
    public Collection<ChessMove> getMoves(TeamColor teamColor, ChessBoard board, ChessPosition myPosition) {
        int myCol = myPosition.getColumn();
        int myRow = myPosition.getRow();
        ParentCalculator calc = new ParentCalculator();
        calc.movesHelp(teamColor, board, myPosition, myRow+2, myCol+1);
        calc.movesHelp(teamColor, board, myPosition, myRow+2, myCol-1);
        calc.movesHelp(teamColor, board, myPosition, myRow-2, myCol-1);
        calc.movesHelp(teamColor, board, myPosition, myRow-2, myCol+1);
        calc.movesHelp(teamColor, board, myPosition, myRow+1, myCol+2);
        calc.movesHelp(teamColor, board, myPosition, myRow+1, myCol-2);
        calc.movesHelp(teamColor, board, myPosition, myRow-1, myCol-2);
        calc.movesHelp(teamColor, board, myPosition, myRow-1, myCol+2);
        return calc.getMoves();
    }

}
