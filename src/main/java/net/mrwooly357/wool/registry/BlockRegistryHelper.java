package net.mrwooly357.wool.registry;

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
     * Registers a custom {@link BlockItem} for the {@code block}.
     * @param id the {@link Identifier}.
     * @param block the {@link Block}.
     */
    static void registerBlockItem(Identifier id, Block block) {
        Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings()));
    }
}
