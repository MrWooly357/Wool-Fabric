package net.mrwooly357.wool.registry.helper;

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
     * @param <E> the {@link Entity}.
     */
    static <E extends Entity> EntityType<E> register(Identifier id, EntityType<E> type) {
        return Registry.register(Registries.ENTITY_TYPE, id, type);
    }
}
