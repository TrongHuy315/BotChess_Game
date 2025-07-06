package com.TrongHuy.botchessgame;

import java.util.List;

public class BitCalculation {
    public static long get4Bit(List<Long> board, int x, int y) {
        return (board.get(y) >> (4 * (Config.getInt("numberBox") - 1 - x))) & 15;
    }

    public static long replace4Bits(long value, int pos, long bits) {
        long repVal = 0b1111L << pos;
        value &= ~repVal;

        return value | (bits << pos);
    }
}
