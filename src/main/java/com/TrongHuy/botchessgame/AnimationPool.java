package com.TrongHuy.botchessgame;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class AnimationPool {
    private static final List<Circle> store = new ArrayList<>();

    public static void init(Pane pane, AnimationListener listener) {
        store.clear();
        int size = Config.getInt("boxSize");

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final int x_target = j;
                final int y_target = i;
                int x = Config.getInt("startBoardX") + (Config.getInt("boxSize") * j) + Config.getInt("boxSize") / 2;
                int y = Config.getInt("startBoardY") + (Config.getInt("boxSize") * i) + Config.getInt("boxSize") / 2;

                Circle circle = new Circle(x, y, Config.getInt("animationRadius"));
                circle.setFill(Color.YELLOW);
                circle.setVisible(false);
                circle.setOnMouseClicked(e -> {
                    listener.AnimationClick(pane, x_target, y_target);
                });

                store.add(circle);
                pane.getChildren().add(circle);
            }
        }
    }

    public static void showAnimation(int x, int y) {
        int size = Config.getInt("boxSize");
        int index = y * size + x;
        if (index >= 0 && index < store.size()) {
            store.get(index).setVisible(true);
            store.get(index).toFront();
        }
    }

    public static void hideAnimation(int x, int y) {
        int size = Config.getInt("boxSize");
        int index = y * size + x;
        if (index >= 0 && index < store.size()) {
            store.get(index).setVisible(false);
        }
    }

    public static void clearAll() {
        for (Circle c : store) {
            c.setVisible(false);
        }
    }
}
