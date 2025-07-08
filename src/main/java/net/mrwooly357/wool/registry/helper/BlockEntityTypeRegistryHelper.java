package net.mrwooly357.wool.registry.helper;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * A helper used for registering custom {@link BlockEntityType}s.
 */
public interface BlockEntityTypeRegistryHelper {


    /**
     * Registers a custom {@link BlockEntityType}.
     * @param id the {@link Identifier}.
     * @param type the {@link BlockEntityType}.
     * @return a registered {@link BlockEntityType}.
     * @param <E> the {@link BlockEntity}.
     */
    static <E extends BlockEntity> BlockEntityType<E> register(Identifier id, BlockEntityType<E> type) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, type);
    }
}
