package net.mrwooly357.wool.util.random;

import net.minecraft.util.math.Direction;

import java.util.List;
import java.util.Map;
import java.util.Random;

public final class RandomUtil {

    private static final Random RANDOM = new Random();


    public static int nextInt(int min, int max) {
        return min >= max ? min : RANDOM.nextInt(max - min + 1) + min;
    }

    public static long nextLong(long min, long max) {
        return min >= max ? min : RANDOM.nextLong(max - min + 1) + min;
    }

    public static float nextFloat(float min, float max) {
        return min >= max ? min : RANDOM.nextFloat() * (max - min) + min;
    }

    public static double nextDouble(double min, double max) {
        return min >= max ? min : RANDOM.nextDouble() * (max - min) + min;
    }

    public static boolean nextBoolean() {
        return RANDOM.nextBoolean();
    }

    public static boolean nextChance(float chance) {
        return chance != 0.0F && (chance == 100.0F || nextFloat(0.0F, 100.0F) <= chance);
    }

    public static Direction nextDirection() {
        return select(List.of(Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.DOWN));
    }

    public static Direction nextVerticalDirection() {
        return select(List.of(Direction.UP, Direction.DOWN));
    }

    public static Direction nextHorizontalDirection() {
        return select(List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST));
    }

    public static <O> O select(List<O> candidates) {
        if (candidates.isEmpty())
            throw new IllegalArgumentException("Can't select an object form an empty list!");

        return candidates.get(nextInt(0, candidates.size()));
    }

    public static <O> O weighted(Map<O, Float> objects) {
        if (!objects.isEmpty()) {
            float totalWeight = 0.0F;

            for (Map.Entry<O, Float> entry : objects.entrySet())
                totalWeight += entry.getValue();

            if (totalWeight != 0.0F) {
                float roll = nextFloat(0.0F, totalWeight);
                float cumulative = 0.0F;

                for (Map.Entry<O, Float> entry : objects.entrySet()) {
                    cumulative += entry.getValue();

                    if (roll <= cumulative)
                        return entry.getKey();
                }
            } else
                throw new IllegalArgumentException("Total weight of all objects is 0.0F!");
        } else
            throw new IllegalArgumentException("No objects to choose from!");

        return null;
    }
}
