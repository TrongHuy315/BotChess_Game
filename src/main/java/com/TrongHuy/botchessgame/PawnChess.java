package com.TrongHuy.botchessgame;

import java.util.ArrayList;
import java.util.List;

public class PawnChess extends Chess {
    public PawnChess(boolean canMove, boolean color, int x, int y) {
        super(canMove, color, x, y, "/images/bp.png", "/images/wp.png");
    }

    @Override
    public List<List<Integer>> getMovedBox(List<Long> board) {
        int x = (this.getX() - Config.getInt("startBoardX")) / Config.getInt("boxSize");
        int y = (this.getY() - Config.getInt("startBoardY")) / Config.getInt("boxSize");
        long color = this.isColor() ? 1 : 0;

        List<List<Integer>> result = new ArrayList<>();

        if (this.getCanMove()) {
            if (y - 1 >= 0) {
                long sub_m = BitCalculation.get4Bit(board, x, y - 1);
                if (sub_m == 0 || (color ^ (sub_m >> 3)) != 0) {
                    result.add(new ArrayList<>(List.of(x, y - 1)));
                }   // check dieu kien di thang

                if (y == 6) {
                    long sub_n = BitCalculation.get4Bit(board, x, y - 2);
                    if (sub_m == 0 && sub_n == 0) {
                        result.add(new ArrayList<>(List.of(x, y - 2)));
                    }   // check dieu kien di 2 o luc xuat phat
                }
            }

            if (y - 1 >= 0) {
                if (x - 1 >= 0) {
                    long sub_1 = BitCalculation.get4Bit(board, x - 1, y - 1);
                    if (sub_1 != 0 && ((sub_1 >> 3) ^ color) != 0) {
                        result.add(new ArrayList<>(List.of(x - 1, y - 1)));
                    }   // check dieu kien an cheo trai
                }

                if (x + 1 < 8) {
                    long sub_2 = BitCalculation.get4Bit(board, x + 1, y - 1);
                    if (sub_2 != 0 && ((sub_2 >> 3) ^ color) != 0) {
                        result.add(new ArrayList<>(List.of(x + 1, y - 1)));
                    }   // check dieu kien an cheo phai
                }
            }

            // chua xu ly truong hop tot duoc phong hau
        }
        else {
            if (y + 1 < 8) {
                long sub_m = BitCalculation.get4Bit(board, x, y + 1);
                if (sub_m == 0 || (color ^ (sub_m >> 3)) != 0) {
                    result.add(new ArrayList<>(List.of(x, y + 1)));
                }   // check dieu kien di thang

                if (y == 1) {
                    long sub_n = BitCalculation.get4Bit(board, x, y + 2);
                    if (sub_m == 0 && sub_n == 0) {
                        result.add(new ArrayList<>(List.of(x, y + 2)));
                    }   // check dieu kien di 2 o luc xuat phat
                }
            }

            if (y + 1 < 8) {
                if (x - 1 >= 0) {
                    long sub_1 = BitCalculation.get4Bit(board, x - 1, y + 1);
                    if (sub_1 != 0 && ((sub_1 >> 3) ^ color) != 0) {
                        result.add(new ArrayList<>(List.of(x - 1, y + 1)));
                    }   // check dieu kien an cheo trai
                }

                if (x + 1 < 8) {
                    long sub_2 = BitCalculation.get4Bit(board, x + 1, y + 1);
                    if (sub_2 != 0 && ((sub_2 >> 3) ^ color) != 0) {
                        result.add(new ArrayList<>(List.of(x + 1, y + 1)));
                    }   // check dieu kien an cheo phai
                }
            }

            // chua xu ly truong hop tot duoc phong hau
        }

        System.out.println(result);

        return result;
    }
}
