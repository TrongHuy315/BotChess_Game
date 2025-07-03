package com.TrongHuy.botchessgame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chess {
    private boolean color;
    private int x;
    private int y;
    private ImageView imageView;

    private final int chessSize = 60;
    private final int restSize = 5;

    public Chess(boolean color, int x, int y, final String imagePath1, final String imagePath2) {
        this.color = color;
        this.x = x;
        this.y = y;

        String imagePath = color != false ? getClass().getResource(imagePath1).toString() : getClass().getResource(imagePath2).toString();
        Image image = new Image(imagePath);

        imageView = new ImageView(image);

        imageView.setFitWidth(this.getChessSize());
        imageView.setFitHeight(this.getChessSize());
        setLayout(this.getX(), this.getY());
    }

    public void setLayout(int x, int y) {
        imageView.setLayoutX(x + this.getRestSize());
        imageView.setLayoutY(y + this.getRestSize());
    }

    public void showChess(Pane pane) {
        pane.getChildren().add(imageView);
    }

    // phuong thuc logic cho quan co
}
