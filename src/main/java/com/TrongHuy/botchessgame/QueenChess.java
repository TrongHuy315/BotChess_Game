package com.TrongHuy.botchessgame;

import java.util.ArrayList;
import java.util.List;

public class QueenChess extends Chess {
    public QueenChess(boolean canMove, boolean color, int x, int y) {
        super(canMove, color, x, y, "/images/bq.png", "/images/wq.png");
    }

    @Override
    public List<List<Integer>> getMovedBox(List<Long> board) {
        int x = (this.getX() - Config.getInt("startBoardX")) / Config.getInt("boxSize");
        int y = (this.getY() - Config.getInt("startBoardY")) / Config.getInt("boxSize");
        long color = this.isColor() ? 1 : 0;

        List<List<Integer>> result = new ArrayList<>();

        int i = 1;
        while (x - i >= 0 && BitCalculation.get4Bit(board, x - i, y) == 0) {
            result.add(new ArrayList<>(List.of(x - i, y)));

            i++;

            if (x - i >= 0 && (color ^ (BitCalculation.get4Bit(board, x - i, y) >> 3)) != 0) {
                result.add(new ArrayList<>(List.of(x - i, y)));

                break;
            }
        }   // check di huong tay

        i = 1;
        while (x + i < 8 && BitCalculation.get4Bit(board, x + i, y) == 0) {
            result.add(new ArrayList<>(List.of(x + i, y)));

            i++;

            if (x + i < 8 && (color ^ (BitCalculation.get4Bit(board, x + i, y) >> 3)) != 0) {
                result.add(new ArrayList<>(List.of(x + i, y)));

                break;
            }
        }   // check di huong dong

        i = 1;
        while (y - i >= 0 && BitCalculation.get4Bit(board, x, y - i) == 0) {
            result.add(new ArrayList<>(List.of(x, y - i)));

            i++;

            if (y - i >= 0 && (color ^ (BitCalculation.get4Bit(board, x, y - i) >> 3)) != 0) {
                result.add(new ArrayList<>(List.of(x, y - i)));

                break;
            }
        }   // check di huong bac

        i = 1;
        while (y + i < 8 && BitCalculation.get4Bit(board, x, y + i) == 0) {
            result.add(new ArrayList<>(List.of(x, y + i)));

            i++;

            if (y + i < 8 && (color ^ (BitCalculation.get4Bit(board, x, y + i) >> 3)) != 0) {
                result.add(new ArrayList<>(List.of(x, y + i)));

                break;
            }
        }   // check di huong nam

        i = 1;
        while (x - i >= 0 && y - i >= 0 && BitCalculation.get4Bit(board, x - i, y - i) == 0) {
            result.add(new ArrayList<>(List.of(x - i, y - i)));

            i++;

            if (x - i >= 0 && y - i >= 0 && (color ^ (BitCalculation.get4Bit(board, x - i, y - i) >> 3)) != 0) {
                result.add(new ArrayList<>(List.of(x - i, y - i)));

                break;
            }
        }   // check di huong tay bac

        i = 1;
        while (x + i < 8 && y - i >= 0 && BitCalculation.get4Bit(board, x + i, y - i) == 0) {
            result.add(new ArrayList<>(List.of(x + i, y - i)));

            i++;

            if (x + i < 8 && y - i >= 0 && (color ^ (BitCalculation.get4Bit(board, x + i, y - i) >> 3)) != 0) {
                result.add(new ArrayList<>(List.of(x + i, y - i)));

                break;
            }
        }   // check di huong dong bac

        i = 1;
        while (x - i >= 0 && y + i < 8 && BitCalculation.get4Bit(board, x - i, y + i) == 0) {
            result.add(new ArrayList<>(List.of(x - i, y + i)));

            i++;

            if (x - i >= 0 && y + i < 8 && (color ^ (BitCalculation.get4Bit(board, x - i, y + i) >> 3)) != 0) {
                result.add(new ArrayList<>(List.of(x - i, y + i)));

                break;
            }
        }   // check di huong tay nam

        i = 1;
        while (x + i < 8 && y + i < 8 && BitCalculation.get4Bit(board, x + i, y + i) == 0) {
            result.add(new ArrayList<>(List.of(x + i, y + i)));

            i++;

            if (x + i < 8 && y + i < 8 && (color ^ (BitCalculation.get4Bit(board, x + i, y + i) >> 3)) != 0) {
                result.add(new ArrayList<>(List.of(x + i, y + i)));

                break;
            }
        }   // check di huong dong nam

        return result;
    }
}
