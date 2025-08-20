package net.mrwooly357.wool.util.random;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

public final class RandomUtil {

    public static final SecureRandom SECURE_RANDOM = new SecureRandom();


    public static int nextInt(int min, int max) {
        return min >= max ? min : SECURE_RANDOM.nextInt(max - min + 1) + min;
    }

    public static long nextLong(long min, long max) {
        return min >= max ? min : SECURE_RANDOM.nextLong(max - min + 1) + min;
    }

    public static float nextFloat(float min, float max) {
        return min >= max ? min : SECURE_RANDOM.nextFloat() * (max - min) + min;
    }

    public static double nextDouble(double min, double max) {
        return min >= max ? min : SECURE_RANDOM.nextDouble() * (max - min) + min;
    }

    public static boolean nextBoolean() {
        return SECURE_RANDOM.nextBoolean();
    }

    public static boolean nextWithChance(float chance) {
        return nextFloat(0.0F, 100.0F) >= chance;
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
