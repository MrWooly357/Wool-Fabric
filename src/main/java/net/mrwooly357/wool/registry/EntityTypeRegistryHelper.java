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
     * @param entityType the {@link EntityType}.
     * @param <Type> the type of {@link Entity}.
     * @return a registered {@link EntityType}.
     */
    static <Type extends Entity> EntityType<Type> register(Identifier id, EntityType<Type> entityType) {
        return Registry.register(Registries.ENTITY_TYPE, id, entityType);
    }
}
