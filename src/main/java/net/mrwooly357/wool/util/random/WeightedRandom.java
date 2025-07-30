package net.mrwooly357.wool.util.random;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.mrwooly357.wool.config.custom.WoolConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface WeightedRandom {


    static @Nullable <T> T floatRandom(Map<T, Float> objects) {
        return floatRandom(Random.create(), objects);
    }

    static @Nullable <T> T floatRandom(Random random, Map<T, Float> objects) {
        if (!objects.isEmpty()) {
            float totalWeight = 0.0F;

            for (Map.Entry<T, Float> entry : objects.entrySet())
                totalWeight += entry.getValue();

            if (totalWeight == 0.0F) {

                if (WoolConfig.enableDeveloperMode)
                    throw new IllegalArgumentException("No given objects have weight: " + objects);
            } else {
                float roll = MathHelper.nextFloat(random, 0.0F, totalWeight);
                float cumulative = 0.0F;

                for (Map.Entry<T, Float> entry : objects.entrySet()) {
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
