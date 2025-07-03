package com.TrongHuy.botchessgame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ChessApp extends Application {
    @Override
    public void start(Stage stage) {
        Pane pane = new Pane();
        pane.setPrefSize(1000,  600);

        Board board = new Board(false);  // user cam quan trang nen den tren, trang duoi
        board.showRawBoard(pane);

        for (int i = 0; i < board.getNumberBox(); i++) {
            for (int j = board.getNumberBox() - 1; j >= 0; j--) {
                int chessType = (int)((board.getBoard().get(i) >> (4 * j)) & 15);
                boolean colorType = chessType > 8 ? true : false;

                int x = board.getStartBoardX() + board.getBoxSize() * j;
                int y = board.getStartBoardY() + board.getBoxSize() * i;
                
                if (chessType == 1 || chessType == 9) {
                    Chess chess = new GeneralChess(colorType, x, y);
                    chess.showChess(pane);
                }
                else if (chessType == 2 || chessType == 10) {
                    Chess chess = new QueenChess(colorType, x, y);
                    chess.showChess(pane);
                }
                else if (chessType == 3 || chessType == 11) {
                    Chess chess = new BishopChess(colorType, x, y);
                    chess.showChess(pane);
                }
                else if (chessType == 4 || chessType == 12) {
                    Chess chess = new KnightChess(colorType, x, y);
                    chess.showChess(pane);
                }
                else if (chessType == 5 || chessType == 13) {
                    Chess chess = new RookChess(colorType, x, y);
                    chess.showChess(pane);
                }
                else if (chessType == 6 || chessType == 14) {
                    Chess chess = new PawnChess(colorType, x, y);
                    chess.showChess(pane);
                }
            }
        }
        
        Scene scene = new Scene(pane, 1000, 600);
        stage.setScene(scene);
        stage.setTitle("Bot Chess Game");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}