package com.TrongHuy.botchessgame;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;

@Getter
public class Board {
    private int startBoardX = 220;
    private int startBoardY = 20;
    private int boxSize = 70;

    private int numberBox = 8;

    private ArrayList<Long> board;

    public Board(boolean value) {
        board = new ArrayList<>(numberBox);

        if (value) {
            board.add(1412567877L);
            board.add(1717986918L);

            for (int i = 2; i < 6; i++) {
                board.add(0L);
            }

            board.add(4008636142L);
            board.add(3703217101L);
        }
        else {
            board.add(3703155661L);
            board.add(4008636142L);

            for (int i = 2; i < 6; i++) {
                board.add(0L);
            }

            board.add(1717986918L);
            board.add(1412506437L);
        }
    }

    public void showRawBoard(Pane pane) {
        for (int i = 0; i < numberBox; i++) {
            for (int j = 0; j < numberBox; j++) {
                Rectangle box = new Rectangle(boxSize, boxSize);
                box.setLayoutX(startBoardX + j * boxSize);
                box.setLayoutY(startBoardY + i * boxSize);

                if (Math.abs(i - j) % 2 == 0) {
                    box.setFill(Color.WHITE);
                }
                else {
                    box.setFill(Color.BROWN);
                }

                box.setStroke(Color.BLACK);

                pane.getChildren().add(box);
            }
        }
    }
}
