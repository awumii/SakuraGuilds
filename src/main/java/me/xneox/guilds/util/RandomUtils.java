package me.xneox.guilds.util;

import java.util.Random;

public final class RandomUtils {
    private static final Random RANDOM = new Random();

    private RandomUtils() {
    }

    public static int getInt(int max) {
        return RANDOM.nextInt(max);
    }

    public static int getInt(int min, int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }

    public static boolean chance(double chance) {
        return RANDOM.nextDouble() * 100.0 <= chance;
    }

    public static String selectRandom(String... possibilities) {
        return possibilities[getInt(possibilities.length)];
    }
}
