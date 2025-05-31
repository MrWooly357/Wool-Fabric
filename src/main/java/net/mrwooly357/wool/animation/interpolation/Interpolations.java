package net.mrwooly357.wool.animation.interpolation;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.registry.ModRegistries;
import net.mrwooly357.wool.registry.ModRegistryKeys;

public class Interpolations {

    public static final Interpolation LINEAR = register(
            "linear", new LinearInterpolation()
    );
    public static final Interpolation EASE_IN = register(
            "ease_in", new EaseInInterpolation()
    );
    public static final Interpolation EASE_OUT = register(
            "ease_out", new EaseOutInterpolation()
    );


    private static Interpolation register(String name, Interpolation interpolation) {
        return register(Identifier.of(Wool.MOD_ID, name), interpolation);
    }

    public static Interpolation register(Identifier id, Interpolation interpolation) {
        return Registry.register(ModRegistries.INTERPOLATION, RegistryKey.of(ModRegistryKeys.INTERPOLATION, id), interpolation);
    }

    public static void init() {
        Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " interpolations");
    }
}