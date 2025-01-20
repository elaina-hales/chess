package chess;

import java.util.Collection;

public interface PieceMovesCalculator {

    // declare functions to be used
    // classes for each piece define these functions in those classes
    // public <classname> implements <interface>
    // use @override in them

    Collection<ChessMove> getMoves();

}
