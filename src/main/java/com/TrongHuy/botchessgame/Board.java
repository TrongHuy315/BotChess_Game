package com.TrongHuy.botchessgame;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;

@Getter
public class Board implements AnimationListener {
    private ArrayList<Long> board;

    private AnimationPool animationPool;
    private Chess selectedChess = null;

    public Board(boolean value) {
        board = new ArrayList<>(Config.getInt("numberBox"));

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
        for (int i = 0; i < Config.getInt("numberBox"); i++) {
            for (int j = 0; j < Config.getInt("numberBox"); j++) {
                Rectangle box = new Rectangle(Config.getInt("boxSize"), Config.getInt("boxSize"));
                box.setLayoutX(Config.getInt("startBoardX") + j * Config.getInt("boxSize"));
                box.setLayoutY(Config.getInt("startBoardY") + i * Config.getInt("boxSize"));

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

        AnimationPool.init(pane, this);   // khoi tao hoat anh sau khi khoi tao ban co de khong bi che
    }

    public void showChess(Pane pane) {
        for (int i = 0; i < Config.getInt("numberBox"); i++) {
            for (int j = Config.getInt("numberBox") - 1; j >= 0; j--) {
                int chessType = (int)((board.get(i) >> (4 * j)) & 15);
                boolean colorType = chessType > 8 ? true : false;

                boolean canMove = i > 1 ? true : false;

                int x = Config.getInt("startBoardX") + Config.getInt("boxSize") * j;
                int y = Config.getInt("startBoardY") + Config.getInt("boxSize") * i;
                
                if (chessType == 1 || chessType == 9) {
                    Chess chess = new GeneralChess(canMove, colorType, x, y);
                    chess.showChess(pane);

                    chess.getImageView().setOnMouseClicked(e -> {
                        if (canMove) {
                            AnimationPool.clearAll();

                            if (!chess.isPressedCheck()) {
                                List<List<Integer>> result = chess.getMovedBox(board);
                                for (List<Integer> arr : result) {
                                    AnimationPool.showAnimation(arr.get(0), arr.get(1));
                                }
                            }

                            chess.setPressedCheck(!chess.isPressedCheck());
                            
                            if (chess.isPressedCheck()) selectedChess = chess;
                            else selectedChess = null;
                        }
                    });
                }
                else if (chessType == 2 || chessType == 10) {
                    Chess chess = new QueenChess(canMove, colorType, x, y);
                    chess.showChess(pane);

                    chess.getImageView().setOnMouseClicked(e -> {
                        if (canMove) {
                            AnimationPool.clearAll();

                            if (!chess.isPressedCheck()) {
                                List<List<Integer>> result = chess.getMovedBox(board);
                                for (List<Integer> arr : result) {
                                    AnimationPool.showAnimation(arr.get(0), arr.get(1));
                                }
                            }

                            chess.setPressedCheck(!chess.isPressedCheck());

                            if (chess.isPressedCheck()) selectedChess = chess;
                            else selectedChess = null;
                        }
                    });
                }
                else if (chessType == 3 || chessType == 11) {
                    Chess chess = new BishopChess(canMove, colorType, x, y);
                    chess.showChess(pane);

                    chess.getImageView().setOnMouseClicked(e -> {
                        if (canMove) {
                            AnimationPool.clearAll();

                            if (!chess.isPressedCheck()) {
                                List<List<Integer>> result = chess.getMovedBox(board);
                                for (List<Integer> arr : result) {
                                    AnimationPool.showAnimation(arr.get(0), arr.get(1));
                                }
                            }

                            chess.setPressedCheck(!chess.isPressedCheck());

                            if (chess.isPressedCheck()) selectedChess = chess;
                            else selectedChess = null;
                        }
                    });
                }
                else if (chessType == 4 || chessType == 12) {
                    Chess chess = new KnightChess(canMove, colorType, x, y);
                    chess.showChess(pane);

                    chess.getImageView().setOnMouseClicked(e -> {
                        if (canMove) {
                            AnimationPool.clearAll();

                            if (!chess.isPressedCheck()) {
                                List<List<Integer>> result = chess.getMovedBox(board);
                                for (List<Integer> arr : result) {
                                    AnimationPool.showAnimation(arr.get(0), arr.get(1));
                                }
                            }

                            chess.setPressedCheck(!chess.isPressedCheck());

                            if (chess.isPressedCheck()) selectedChess = chess;
                            else selectedChess = null;
                        }
                    });
                }
                else if (chessType == 5 || chessType == 13) {
                    Chess chess = new RookChess(canMove, colorType, x, y);
                    chess.showChess(pane);

                    chess.getImageView().setOnMouseClicked(e -> {
                        if (canMove) {
                            AnimationPool.clearAll();

                            if (!chess.isPressedCheck()) {
                                List<List<Integer>> result = chess.getMovedBox(board);
                                for (List<Integer> arr : result) {
                                    AnimationPool.showAnimation(arr.get(0), arr.get(1));
                                }
                            }

                            chess.setPressedCheck(!chess.isPressedCheck());

                            if (chess.isPressedCheck()) selectedChess = chess;
                            else selectedChess = null;
                        }
                    });
                }
                else if (chessType == 6 || chessType == 14) {
                    Chess chess = new PawnChess(canMove, colorType, x, y);
                    chess.showChess(pane);

                    chess.getImageView().setOnMouseClicked(e -> {
                        if (canMove) {
                            AnimationPool.clearAll();

                            if (!chess.isPressedCheck()) {
                                List<List<Integer>> result = chess.getMovedBox(board);
                                for (List<Integer> arr : result) {
                                    AnimationPool.showAnimation(arr.get(0), arr.get(1));
                                }
                            }

                            chess.setPressedCheck(!chess.isPressedCheck());

                            if (chess.isPressedCheck()) selectedChess = chess;
                            else selectedChess = null;
                        }
                    });
                }
            }
        }
    }

    @Override
    public void AnimationClick(int x_target, int y_target) {   // toa do x, y theo ma tran
        if (selectedChess == null) return;

        int x_0 = (selectedChess.getX() - Config.getInt("startBoardX")) / Config.getInt("boxSize");
        int y_0 = (selectedChess.getY() - Config.getInt("startBoardY")) / Config.getInt("boxSize");

        // thuc hien update board
        long src = (board.get(y_0) >> ((Config.getInt("numberBox") - 1 - x_0) * 4)) & 15;
        System.out.println(x_0);
        System.out.println(y_0);
        board.set(y_0, BitCalculation.replace4Bits(board.get(y_0), (Config.getInt("numberBox") - 1 - x_0) * 4, 0));
        board.set(y_target, BitCalculation.replace4Bits(board.get(y_target), (Config.getInt("numberBox") - 1 - x_target) * 4, src));

        // thuc hien di chuyen quan co
        selectedChess.setX(Config.getInt("startBoardX") + x_target * Config.getInt("boxSize"));
        selectedChess.setY(Config.getInt("startBoardY") + y_target * Config.getInt("boxSize"));

        // thuc hien cap nhat layout quan co
        selectedChess.getImageView().setLayoutX(selectedChess.getX() + Config.getInt("restSize"));
        selectedChess.getImageView().setLayoutY(selectedChess.getY() + Config.getInt("restSize"));

        AnimationPool.clearAll();
        selectedChess = null;
    }
}
