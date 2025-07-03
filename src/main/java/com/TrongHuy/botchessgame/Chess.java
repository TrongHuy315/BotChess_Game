package com.TrongHuy.botchessgame;

import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chess {
    private boolean color;
    private int x;
    private int y;

    private final int chessSize = 60;
    private final int restSize = 5;

    public Chess(boolean color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public void setLayout(int x, int y) {

    }

    public void showChess(Pane pane) {

    }

    // phuong thuc logic cho quan co
}
