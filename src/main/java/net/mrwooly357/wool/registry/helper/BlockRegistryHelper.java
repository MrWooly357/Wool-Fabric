package net.mrwooly357.wool.registry.helper;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * A helper used for registering custom {@link Block}s.
 */
public interface BlockRegistryHelper {


    /**
     * Registers a custom {@link Block}.
     * @param id the {@link Identifier}.
     * @param block the {@link Block}.
     * @return a registered {@link Block}.
     */
    static Block register(Identifier id, Block block) {
        return Registry.register(Registries.BLOCK, id, block);
    }

    /**
     * Registers a custom {@link BlockItem}.
     * @param id the {@link Identifier}.
     * @param item the {@link BlockItem}.
     * @return a registered {@link BlockItem}.
     */
    static Item registerBlockItem(Identifier id, BlockItem item) {
        return Registry.register(Registries.ITEM, id, item);
    }
}
