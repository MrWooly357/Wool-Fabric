package net.mrwooly357.wool.registry;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.entity.util.AccessoryInventoryUnit;

/**
 * A helper used for registering custom player {@link AccessoryInventoryUnit}s.
 */
public interface PlayerAccessoryInventoryRegistryHelper {


    /**
     * Registers a custom player {@link AccessoryInventoryUnit}.
     * @param id the {@link Identifier}.
     * @param unit the {@link AccessoryInventoryUnit}.
     * @return a registered player {@link AccessoryInventoryUnit}.
     */
    static AccessoryInventoryUnit register(Identifier id, AccessoryInventoryUnit unit) {
        return Registry.register(WoolRegistries.PLAYER_ACCESSORY_INVENTORY, id, unit);
    }
}
