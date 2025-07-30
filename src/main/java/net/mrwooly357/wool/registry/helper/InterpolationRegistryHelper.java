package net.mrwooly357.wool.registry.helper;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.animation.interpolation.Interpolation;
import net.mrwooly357.wool.registry.WoolRegistries;

/**
 * A helper used for registering custom {@link Interpolation}s.
 */
public interface InterpolationRegistryHelper {


    /**
     * Registers a custom {@link Interpolation}.
     * @param id the {@link Identifier}.
     * @param interpolation the {@link Interpolation}.
     * @return a registered {@link Interpolation}.
     */
    static Interpolation register(Identifier id, Interpolation interpolation) {
        return Registry.register(WoolRegistries.INTERPOLATION, id, interpolation);
    }
}
