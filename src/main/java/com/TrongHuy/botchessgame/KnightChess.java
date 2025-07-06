package com.TrongHuy.botchessgame;

import java.util.ArrayList;
import java.util.List;

public class KnightChess extends Chess {
    public KnightChess(boolean canMove, boolean color, int x, int y) {
        super(canMove, color, x, y, "/images/bn.png", "/images/wn.png");
    }

    @Override
    public List<List<Integer>> getMovedBox(List<Long> board) {
        int x = (this.getX() - Config.getInt("startBoardX")) / Config.getInt("boxSize");
        int y = (this.getY() - Config.getInt("startBoardY")) / Config.getInt("boxSize");
        long color = this.isColor() ? 1 : 0;

        List<List<Integer>> result = new ArrayList<>();

        if (x - 1 >= 0) {
            if (y - 2 >= 0) {
                long sub = BitCalculation.get4Bit(board, x - 1, y - 2);
                if (sub == 0 || (color ^ (sub >> 3)) != 0) {
                    result.add(new ArrayList<>(List.of(x - 1, y - 2)));
                }
            }

            if (y + 2 < 8) {
                long sub = BitCalculation.get4Bit(board, x - 1, y + 2);
                if (sub == 0 || (color ^ (sub >> 3)) != 0) {
                    result.add(new ArrayList<>(List.of(x - 1, y + 2)));
                }
            }
        }

        if (x + 1 < 8) {
            if (y - 2 >= 0) {
                long sub = BitCalculation.get4Bit(board, x + 1, y - 2);
                if (sub == 0 || (color ^ (sub >> 3)) != 0) {
                    result.add(new ArrayList<>(List.of(x + 1, y - 2)));
                }
            }

            if (y + 2 < 8) {
                long sub = BitCalculation.get4Bit(board, x + 1, y + 2);
                if (sub == 0 || (color ^ (sub >> 3)) != 0) {
                    result.add(new ArrayList<>(List.of(x + 1, y + 2)));
                }
            }
        }

        if (x - 2 >= 0) {
            if (y - 1 >= 0) {
                long sub = BitCalculation.get4Bit(board, x - 2, y - 1);
                if (sub == 0 || (color ^ (sub >> 3)) != 0) {
                    result.add(new ArrayList<>(List.of(x - 2, y - 1)));
                }
            }

            if (y + 1 < 8) {
                long sub = BitCalculation.get4Bit(board, x - 2, y + 1);
                if (sub == 0 || (color ^ (sub >> 3)) != 0) {
                    result.add(new ArrayList<>(List.of(x - 2, y + 1)));
                }
            }
        }

        if (x + 2 < 8) {
            if (y - 1 >= 0) {
                long sub = BitCalculation.get4Bit(board, x + 2, y - 1);
                if (sub == 0 || (color ^ (sub >> 3)) != 0) {
                    result.add(new ArrayList<>(List.of(x + 2, y - 1)));
                }
            }

            if (y + 1 < 8) {
                long sub = BitCalculation.get4Bit(board, x + 2, y + 1);
                if (sub == 0 || (color ^ (sub >> 3)) != 0) {
                    result.add(new ArrayList<>(List.of(x + 2, y + 1)));
                }
            }
        }

        return result;
    }
}
