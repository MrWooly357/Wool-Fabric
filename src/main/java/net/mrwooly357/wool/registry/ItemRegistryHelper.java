package net.mrwooly357.wool.registry;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * A helper used for registering custom {@link Item}s.
 */
public interface ItemRegistryHelper {


    /**
     * Registers a custom {@link Item}.
     * @param id the {@link Identifier}.
     * @param item the {@link Item}.
     * @return a registered {@link Item}.
     */
    static Item register(Identifier id, Item item) {
        return Registry.register(Registries.ITEM, id, item);
    }
}
