package com.TrongHuy.botchessgame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ChessApp extends Application {
    @Override
    public void start(Stage stage) {
        Config.load("./config.properties");

        Pane pane = new Pane();
        pane.setPrefSize(1000,  600);

        Board board = new Board();
        board.showRawBoard(pane);
        board.showChess(pane);
        board.firstBotRun(pane);
        
        Scene scene = new Scene(pane, 1000, 600);
        stage.setScene(scene);
        stage.setTitle("Bot Chess Game");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}