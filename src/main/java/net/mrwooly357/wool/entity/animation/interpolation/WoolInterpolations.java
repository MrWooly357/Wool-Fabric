package net.mrwooly357.wool.entity.animation.interpolation;

import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.helper.InterpolationRegistryHelper;

public class WoolInterpolations {

    public static final Interpolation LINEAR = register(
            "linear", progress -> progress
    );


    private static Interpolation register(String name, Interpolation interpolation) {
        return InterpolationRegistryHelper.register(Identifier.of(Wool.MOD_ID, name), interpolation);
    }

    public static void initialize() {
        if (WoolConfig.developerMode)
            Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " interpolations");
    }
}
