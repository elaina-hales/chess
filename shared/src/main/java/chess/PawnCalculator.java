package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnCalculator implements PieceMovesCalculator {
    public PawnCalculator(){
    }


    public Collection<ChessMove> promote(ChessGame.TeamColor teamColor, ChessPosition myPosition, ChessPosition endPos, int myRow, Collection<ChessMove> moves){
        if ((myRow == 8) && teamColor.equals(ChessGame.TeamColor.WHITE)) {
            ChessMove q_promotion = new ChessMove(myPosition, endPos, ChessPiece.PieceType.QUEEN);
            ChessMove r_promotion = new ChessMove(myPosition, endPos, ChessPiece.PieceType.ROOK);
            ChessMove k_promotion = new ChessMove(myPosition, endPos, ChessPiece.PieceType.KNIGHT);
            ChessMove b_promotion = new ChessMove(myPosition, endPos, ChessPiece.PieceType.BISHOP);
            moves.add(q_promotion);
            moves.add(r_promotion);
            moves.add(k_promotion);
            moves.add(b_promotion);
        } else if ((myRow == 1) && teamColor.equals(ChessGame.TeamColor.BLACK)){
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
        return moves;
    }

    public Collection<ChessMove> moveForwardOne(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        int b_curRow = myRow-1;
        int w_curRow = myRow+1;
        if (teamColor == ChessGame.TeamColor.WHITE) {
            if ((myCol < 9) && (myRow < 9)) {
                ChessPosition endPos = new ChessPosition(myRow, myCol);
                ChessPiece curPiece = board.getPiece(endPos);
                if (curPiece == null) {
                    moves = promote(teamColor, myPosition, endPos, w_curRow, moves);
                }
            }
            return moves;
        } else {
            if ((myCol >= 1) && (myRow >= 1)) {
                ChessPosition endPos = new ChessPosition(myRow, myCol);
                ChessPiece curPiece = board.getPiece(endPos);
                if (curPiece == null) {
                    moves = promote(teamColor, myPosition, endPos, b_curRow, moves);
                }
            }
            return moves;
        }
    }

    public Collection<ChessMove> capture(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        int newRow = myRow+1;
        int right = myCol+1;
        int left = myCol-1;
        if ((right >= 1) && (right < 9) && (newRow >= 1)) {
            ChessPosition rightPos = new ChessPosition(newRow, right);
            ChessPosition leftPos = new ChessPosition(newRow, left);
            ChessPiece leftPiece = board.getPiece(leftPos);
            ChessPiece rightPiece = board.getPiece(rightPos);
            if (leftPiece != null){
                if (!leftPiece.getTeamColor().equals(teamColor)){
                    moves = promote(teamColor, myPosition, leftPos, newRow, moves);
                }
            }
            if (rightPiece != null){
                if (!rightPiece.getTeamColor().equals(teamColor)){
                    moves = promote(teamColor, myPosition, leftPos, newRow, moves);
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
        // forwards and backwards are different for different color pawns
        moves = moveForwardOne(teamColor, board, myPosition, myRow+1, myCol, moves);
        moves = moveForwardOne(teamColor, board, myPosition, myRow-1, myCol, moves);
        moves = capture(teamColor, board, myPosition, myRow, myCol, moves);
        if ((myRow == 2) && (teamColor == ChessGame.TeamColor.WHITE)){
            ChessPosition curPos = new ChessPosition(myRow+2, myCol);
            ChessMove cur = new ChessMove(myPosition, curPos, null);
            moves.add(cur);
        } else if ((myRow == 7) && (teamColor == ChessGame.TeamColor.BLACK)){
            ChessPosition curPos = new ChessPosition(myRow-2, myCol);
            ChessMove cur = new ChessMove(myPosition, curPos, null);
            moves.add(cur);
        }
       // moves = movesHelp(teamColor, board, myPosition, myRow+2, myCol, moves);
       // moves = movesHelp(teamColor, board, myPosition, myRow, myCol+1, moves);
       // moves = movesHelp(teamColor, board, myPosition, myRow, myCol-1, moves);
        return moves;
    }

}
