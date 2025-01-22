package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnCalculator implements PieceMovesCalculator {
    public PawnCalculator(){
    }

    public Collection<ChessMove> moveForwardOne(ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        if ((myCol <= 8 ) && (myRow <= 8)) {
            ChessPosition endPos = new ChessPosition(myRow, myCol);
            ChessPiece curPiece = board.getPiece(endPos);
            if (curPiece == null) {
                if (myRow == 8){
                    ChessMove q_promotion = new ChessMove(myPosition, endPos, ChessPiece.PieceType.QUEEN);
                    ChessMove r_promotion = new ChessMove(myPosition, endPos, ChessPiece.PieceType.ROOK);
                    ChessMove k_promotion = new ChessMove(myPosition, endPos, ChessPiece.PieceType.KNIGHT);
                    ChessMove b_promotion = new ChessMove(myPosition, endPos, ChessPiece.PieceType.BISHOP);
                    moves.add(q_promotion);
                    moves.add(r_promotion);
                    moves.add(k_promotion);
                    moves.add(b_promotion);
                } else {
                    ChessMove cur = new ChessMove(myPosition, endPos, null);
                    moves.add(cur);
                }
            }
        }
        return moves;
    }

    public Collection<ChessMove> capture(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        int newRow = myRow+1;
        int rightCapture = myCol+1;
        int leftCapture = myCol-1;
        if ((rightCapture <= 8) && (leftCapture >= 1) && (newRow <= 8)) {
            ChessPosition rightPos = new ChessPosition(newRow, rightCapture);
            ChessPosition leftPos = new ChessPosition(newRow, leftCapture);
            ChessPiece leftCur = board.getPiece(leftPos);
            ChessPiece rightCur = board.getPiece(rightPos);
            if (leftCur != null){
                if (!leftCur.getTeamColor().equals(teamColor)){
                    if (leftCapture == 8){
                        ChessMove q_promotion = new ChessMove(myPosition, leftPos, ChessPiece.PieceType.QUEEN);
                        ChessMove r_promotion = new ChessMove(myPosition, leftPos, ChessPiece.PieceType.ROOK);
                        ChessMove k_promotion = new ChessMove(myPosition, leftPos, ChessPiece.PieceType.KNIGHT);
                        ChessMove b_promotion = new ChessMove(myPosition, leftPos, ChessPiece.PieceType.BISHOP);
                        moves.add(q_promotion);
                        moves.add(r_promotion);
                        moves.add(k_promotion);
                        moves.add(b_promotion);

                    } else {
                        ChessMove cur = new ChessMove(myPosition, leftPos, null);
                        moves.add(cur);
                    }
                }
            }
            if (rightCur != null){
                if (!rightCur.getTeamColor().equals(teamColor)){
                    if (leftCapture == 8){
                        ChessMove q_promotion = new ChessMove(myPosition, rightPos, ChessPiece.PieceType.QUEEN);
                        ChessMove r_promotion = new ChessMove(myPosition, rightPos, ChessPiece.PieceType.ROOK);
                        ChessMove k_promotion = new ChessMove(myPosition, rightPos, ChessPiece.PieceType.KNIGHT);
                        ChessMove b_promotion = new ChessMove(myPosition, rightPos, ChessPiece.PieceType.BISHOP);
                        moves.add(q_promotion);
                        moves.add(r_promotion);
                        moves.add(k_promotion);
                        moves.add(b_promotion);
                    } else {
                        ChessMove cur = new ChessMove(myPosition, rightPos, null);
                        moves.add(cur);
                    }
                }

            }
        }
        return moves;
    }


    @Override
    public Collection<ChessMove> getMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int myCol = myPosition.getColumn();
        int myRow = myPosition.getRow();
        moves = moveForwardOne(board, myPosition, myRow+1, myCol, moves);
        moves = capture(teamColor, board, myPosition, myRow, myCol, moves);
       // moves = movesHelp(teamColor, board, myPosition, myRow+2, myCol, moves);
       // moves = movesHelp(teamColor, board, myPosition, myRow, myCol+1, moves);
       // moves = movesHelp(teamColor, board, myPosition, myRow, myCol-1, moves);
        return moves;
    }

}
