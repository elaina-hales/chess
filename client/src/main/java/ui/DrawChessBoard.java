package ui;

import chess.*;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class DrawChessBoard {
    private final ChessGame chess;

    public DrawChessBoard(ChessGame chess){
        this.chess = chess;
    }

    public void draw(){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

//        drawHeaders(out);
//
//        drawTicTacToeBoard(out);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }







}
