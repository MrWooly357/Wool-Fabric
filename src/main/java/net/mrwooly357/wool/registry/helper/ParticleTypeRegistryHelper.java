package net.mrwooly357.wool.registry.helper;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * A helper used for registering custom {@link ParticleType}s.
 */
public interface ParticleTypeRegistryHelper {


    /**
     * Registers a custom {@link ParticleType}.
     * @param id the {@link Identifier}.
     * @param type the {@link ParticleType}.
     * @return a registered {@link ParticleType}.
     * @param <E> the {@link ParticleEffect}.
     */
    static <E extends ParticleEffect>ParticleType<E> register(Identifier id, ParticleType<E> type) {
        return Registry.register(Registries.PARTICLE_TYPE, id, type);
    }
}
