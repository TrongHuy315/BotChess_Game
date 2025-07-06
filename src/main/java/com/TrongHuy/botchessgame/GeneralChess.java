package com.TrongHuy.botchessgame;

import java.util.ArrayList;
import java.util.List;

public class GeneralChess extends Chess {
    public GeneralChess(boolean canMove, boolean color, int x, int y) {
        super(canMove, color, x, y, "/images/bk.png", "/images/wk.png");
    }

    @Override
    public List<List<Integer>> getMovedBox(List<Long> board) {
        int x = (this.getX() - Config.getInt("startBoardX")) / Config.getInt("boxSize");
        int y = (this.getY() - Config.getInt("startBoardY")) / Config.getInt("boxSize");
        long color = this.isColor() ? 1 : 0;

        List<List<Integer>> result = new ArrayList<>();

        if (y - 1 >= 0) {
            long sub_1 = BitCalculation.get4Bit(board, x, y - 1);
            if (sub_1 == 0 || (color ^ (sub_1 >> 3)) != 0) {
                result.add(new ArrayList<>(List.of(x, y - 1)));
            }   // check dieu kien di huong bac

            long sub_2 = BitCalculation.get4Bit(board, x - 1, y - 1);
            if (sub_2 == 0 || (color ^ (sub_2 >> 3)) != 0) {
                result.add(new ArrayList<>(List.of(x - 1, y - 1)));
            }   // check dieu kien di huong tay bac

            long sub_3 = BitCalculation.get4Bit(board, x + 1, y - 1);
            if (sub_3 == 0 || (color ^ (sub_3 >> 3)) != 0) {
                result.add(new ArrayList<>(List.of(x + 1, y - 1)));
            }   // check dieu kien di huong dong bac
        }

        if (y + 1 < 8) {
            long sub_1 = BitCalculation.get4Bit(board, x, y + 1);
            if (sub_1 == 0 || (color ^ (sub_1 >> 3)) != 0) {
                result.add(new ArrayList<>(List.of(x, y + 1)));
            }   // check dieu kien di huong nam

            long sub_2 = BitCalculation.get4Bit(board, x - 1, y + 1);
            if (sub_2 == 0 || (color ^ (sub_2 >> 3)) != 0) {
                result.add(new ArrayList<>(List.of(x - 1, y + 1)));
            }   // check dieu kien di huong tay nam

            long sub_3 = BitCalculation.get4Bit(board, x + 1, y + 1);
            if (sub_3 == 0 || (color ^ (sub_3 >> 3)) != 0) {
                result.add(new ArrayList<>(List.of(x + 1, y + 1)));
            }   // check dieu kien di huong dong nam
        }

        long sub_1 = BitCalculation.get4Bit(board, x - 1, y);
        if ((x - 1 >= 0 && sub_1 == 0) || (color ^ (sub_1 >> 3)) != 0) {
            result.add(new ArrayList<>(List.of(x - 1, y)));
        }   // check dieu kien di huong tay

        long sub_2 = BitCalculation.get4Bit(board, x + 1, y);
        if ((x + 1 < 8 && sub_2 == 0) || (color ^ (sub_2 >> 3)) != 0) {
            result.add(new ArrayList<>(List.of(x + 1, y)));
        }   // check dieu kien di huong dong

        return result;
    }
}
