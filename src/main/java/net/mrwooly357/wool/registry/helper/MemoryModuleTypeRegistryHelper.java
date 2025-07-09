package net.mrwooly357.wool.registry.helper;

import com.mojang.serialization.Codec;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Optional;

/**
 * A helper used for registering custom {@link MemoryModuleType}s.
 */
public interface MemoryModuleTypeRegistryHelper {


    /**
     * Registers a custom {@link MemoryModuleType}.
     * @param id the {@link Identifier}.
     * @return a registered {@link MemoryModuleType}.
     * @param <U> the type of data.
     */
    static <U> MemoryModuleType<U> register(Identifier id) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, id, new MemoryModuleType<>(Optional.empty()));
    }

    /**
     * Registers a custom {@link MemoryModuleType}.
     * @param id the {@link Identifier}.
     * @param codec the {@link Codec}.
     * @return a registered {@link MemoryModuleType}.
     * @param <U> the type of data.
     */
    static <U> MemoryModuleType<U> register(Identifier id, Codec<U> codec) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, id, new MemoryModuleType<>(Optional.of(codec)));
    }
}
