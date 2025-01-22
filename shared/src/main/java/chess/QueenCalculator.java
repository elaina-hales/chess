package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenCalculator implements PieceMovesCalculator {
    public QueenCalculator(){
    }

    public Collection<ChessMove> forwardMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        for (int i = myRow+1; i < 9; i++){
            ChessPosition endPos = new ChessPosition(i, myCol);
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


    public Collection<ChessMove> backwardMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        for (int i = myRow-1; i >= 1 ; i--){
            ChessPosition endPos = new ChessPosition(i, myCol);
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


    public Collection<ChessMove> rightMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        for (int i = myCol+1; i < 9; i++){
            ChessPosition endPos = new ChessPosition(myRow, i);
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

    public Collection<ChessMove> leftMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        for (int i = myCol-1; i >= 1; i--){
            ChessPosition endPos = new ChessPosition(myRow, i);
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

    public Collection<ChessMove> forwardRightMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        int tempCol = myCol+1;
        int tempRow = myRow+1;
        while ((tempCol < 9) && (tempRow < 9)) {
            ChessPosition endPos = new ChessPosition(tempRow, tempCol);
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
            tempCol++;
            tempRow++;
        }
        return moves;
    }

    public Collection<ChessMove> forwardLeftMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves) {
        int tempCol = myCol - 1;
        int tempRow = myRow + 1;
        while ((tempCol >= 1) && (tempRow < 9)) {
            ChessPosition endPos = new ChessPosition(tempRow, tempCol);
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
            tempCol--;
            tempRow++;
        }
        return moves;
    }


    public Collection<ChessMove> backLeftMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        int tempCol = myCol-1;
        int tempRow = myRow-1;
        while ((tempCol >= 1) && (tempRow >= 1)) {
            ChessPosition endPos = new ChessPosition(tempRow, tempCol);
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
            tempCol--;
            tempRow--;
        }
        return moves;
    }


    public Collection<ChessMove> backRightMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition, int myRow, int myCol, Collection<ChessMove> moves){
        int tempCol = myCol+1;
        int tempRow = myRow-1;
        while ((tempCol < 9) && (tempRow >= 1)) {
            ChessPosition endPos = new ChessPosition(tempRow, tempCol);
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
            tempCol++;
            tempRow--;
        }
        return moves;
    }

    @Override
    public Collection<ChessMove> getMoves(ChessGame.TeamColor teamColor, ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int myCol = myPosition.getColumn();
        int myRow = myPosition.getRow();
        moves = forwardMoves(teamColor, board, myPosition, myRow, myCol, moves);
        moves = backwardMoves(teamColor, board, myPosition, myRow, myCol, moves);
        moves = rightMoves(teamColor, board, myPosition, myRow, myCol, moves);
        moves = leftMoves(teamColor, board, myPosition, myRow, myCol, moves);
        moves = forwardLeftMoves(teamColor, board, myPosition, myRow, myCol, moves);
        moves = forwardRightMoves(teamColor, board, myPosition, myRow, myCol, moves);
        moves = backLeftMoves(teamColor, board, myPosition, myRow, myCol, moves);
        moves = backRightMoves(teamColor, board, myPosition, myRow, myCol, moves);
        return moves;
    }

}
