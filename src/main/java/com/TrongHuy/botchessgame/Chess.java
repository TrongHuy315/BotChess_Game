package com.TrongHuy.botchessgame;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chess {
    private ImageView imageView;
    private Boolean canMove;

    private boolean color;
    private int x;
    private int y;

    private boolean pressedCheck = false;

    public Chess(boolean canMove, boolean color, int x, int y, final String imagePath1, final String imagePath2) {
        this.canMove = canMove;
        this.color = color;
        this.x = x;
        this.y = y;

        String imagePath = color != false ? getClass().getResource(imagePath1).toString() : getClass().getResource(imagePath2).toString();
        Image image = new Image(imagePath);

        imageView = new ImageView(image);

        imageView.setFitWidth(Config.getInt("chessSize"));
        imageView.setFitHeight(Config.getInt("chessSize"));
        setLayout(this.getX(), this.getY());
    }

    public void setLayout(int x, int y) {
        imageView.setLayoutX(x + Config.getInt("restSize"));
        imageView.setLayoutY(y + Config.getInt("restSize"));
    }

    public void showChess(Pane pane) {
        pane.getChildren().add(imageView);
    }

    public List<List<Integer>> getMovedBox(List<Long> board) {
        return null;
    }

    // phuong thuc logic cho quan co
}
