package chess;

import java.util.Collection;

public class RookCalculator implements PieceMovesCalculator{
    private ChessPiece rook;
    public RookCalculator(ChessPiece rook){
        this.rook = rook;
    }

    @Override
    public Collection<ChessMove> getMoves(){
        Collection<ChessMove> collect;
        return collect;
    }
}
