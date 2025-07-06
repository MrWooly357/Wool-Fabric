package net.mrwooly357.wool.registry.helper;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.entity.accessory.inventory.AccessoryInventoryUnit;
import net.mrwooly357.wool.registry.WoolRegistries;

/**
 * A helper used for registering custom empty {@link AccessoryInventoryUnit}s.
 */
public interface EmptyAccessoryInventoryRegistryHelper {


    /**
     * Registers a custom empty {@link AccessoryInventoryUnit}.
     * @param id the {@link Identifier}.
     * @param unit the {@link AccessoryInventoryUnit}.
     * @return a registered empty {@link AccessoryInventoryUnit}.
     */
    static AccessoryInventoryUnit register(Identifier id, AccessoryInventoryUnit unit) {
        return Registry.register(WoolRegistries.EMPTY_ACCESSORY_INVENTORY, id, unit);
    }
}
