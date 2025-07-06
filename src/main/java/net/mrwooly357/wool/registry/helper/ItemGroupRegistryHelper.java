package net.mrwooly357.wool.registry.helper;

import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * A helper used for registering custom {@link ItemGroup}s.
 */
public interface ItemGroupRegistryHelper {


    /**
     * Registers a custom {@link ItemGroup}.
     * @param id the {@link Identifier}.
     * @param group the {@link ItemGroup}.
     * @return a registered {@link ItemGroup}.
     */
    static ItemGroup register(Identifier id, ItemGroup group) {
        return Registry.register(Registries.ITEM_GROUP, id, group);
    }
}
