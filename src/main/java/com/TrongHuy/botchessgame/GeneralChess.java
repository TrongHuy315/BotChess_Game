package com.TrongHuy.botchessgame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class GeneralChess extends Chess {
    private ImageView imageView;
    public GeneralChess(boolean color, int x, int y) {
        super(color, x, y);

        String imagePath = color != false ? getClass().getResource("/images/bk.png").toString() : getClass().getResource("/images/wk.png").toString();
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
