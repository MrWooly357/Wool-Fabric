package net.mrwooly357.wool.registry;

import net.minecraft.entity.Entity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.entity.EntityType;

/**
 * A helper used for registering custom {@link EntityType}s.
 */
public interface EntityTypeRegistryHelper {


    /**
     * Registers a custom {@link EntityType}.
     * @param id  the {@link Identifier}.
     * @param type the {@link EntityType}.
     * @return a registered {@link EntityType}.
     * @param <T> the {@link Entity}.
     */
    static <T extends Entity> EntityType<T> register(Identifier id, EntityType<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, id, type);
    }
}
