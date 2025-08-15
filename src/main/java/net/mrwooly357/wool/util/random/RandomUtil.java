package net.mrwooly357.wool.util.random;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.mrwooly357.wool.config.custom.WoolConfig;
import org.jetbrains.annotations.Nullable;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

public final class RandomUtil {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final Random RANDOM = Random.create();


    public static <O> O select(List<O> candidates) {
        if (candidates.isEmpty())
            throw new IllegalArgumentException("Can't select an object form an empty list!");

        return candidates.get(SECURE_RANDOM.nextInt(candidates.size()));
    }

    @Nullable
    public static <O> O weighted(Map<O, Float> objects) {
        if (!objects.isEmpty()) {
            float totalWeight = 0.0F;

            for (Map.Entry<O, Float> entry : objects.entrySet())
                totalWeight += entry.getValue();

            if (totalWeight == 0.0F) {

                if (WoolConfig.enableDeveloperMode)
                    throw new IllegalArgumentException("No given objects have weight: " + objects);
            } else {
                float roll = MathHelper.nextFloat(RANDOM, 0.0F, totalWeight);
                float cumulative = 0.0F;

                for (Map.Entry<O, Float> entry : objects.entrySet()) {
                    cumulative += entry.getValue();

                    if (roll <= cumulative)
                        return entry.getKey();
                }
            }
        } else if (WoolConfig.enableDeveloperMode)
            throw new IllegalArgumentException("No objects to choose from!");

        return null;
    }
}
