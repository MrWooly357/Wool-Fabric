package net.mrwooly357.wool.registry;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.entity.util.AccessoryInventoryUnit;

/**
 * A helper used for registering custom {@link Registry entity accessory inventories}.
 */
public interface EntityAccessoryInventoryRegistryHelper {


    /**
     * Registers a custom {@link Registry entity accessory inventory}.
     * @param id the {@link Identifier}.
     * @param inventory the {@link Registry entity accessory inventory}.
     * @return a registered {@link Registry entity accessory inventory}.
     */
    static Registry<AccessoryInventoryUnit> register(Identifier id, Registry<AccessoryInventoryUnit> inventory) {
        return Registry.register(WoolRegistries.ENTITY_ACCESSORY_INVENTORY, id, inventory);
    }
}
