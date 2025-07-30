package net.mrwooly357.wool.animation.interpolation;

import net.minecraft.util.Identifier;
import net.mrwooly357.wool.animation.interpolation.custom.AdvancedInterpolation;
import net.mrwooly357.wool.animation.interpolation.custom.SimpleInterpolation;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.helper.InterpolationRegistryHelper;

public class WoolInterpolations {

    public static final Interpolation LINEAR = register(
            "linear", (SimpleInterpolation) (t, a, b) -> a + (b - a) * t
    );
    public static final Interpolation CATMULL_ROM = register(
            "catmull_rom", (AdvancedInterpolation) (t, a, b, previous, next) -> 0.5F * (2 * a + (b - previous) * t + (2 * previous - 5 * a + 4 * b - next) * t * t + (next - previous + 3 * (a - b)) * t * t * t)
    );


    private static Interpolation register(String name, Interpolation interpolation) {
        return InterpolationRegistryHelper.register(Identifier.of(Wool.MOD_ID, name), interpolation);
    }

    public static void initialize() {
        if (WoolConfig.enableDeveloperMode)
            Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " interpolations");
    }
}
