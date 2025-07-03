package com.TrongHuy.botchessgame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class RookChess extends Chess {
    private ImageView imageView;
    public RookChess(boolean color, int x, int y) {
        super(color, x, y);

        String imagePath = color != false ? getClass().getResource("/images/br.png").toString() : getClass().getResource("/images/wr.png").toString();
        Image image = new Image(imagePath);

        imageView = new ImageView(image);

        imageView.setFitWidth(this.getChessSize());
        imageView.setFitHeight(this.getChessSize());
        setLayout(this.getX(), this.getY());
    }

    @Override
    public void setLayout(int x, int y) {
        imageView.setLayoutX(x + this.getRestSize());
        imageView.setLayoutY(y + this.getRestSize());
    }

    @Override
    public void showChess(Pane pane) {
        pane.getChildren().add(imageView);
    }
}
