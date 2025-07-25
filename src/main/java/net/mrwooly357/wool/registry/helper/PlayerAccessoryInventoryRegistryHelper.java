package net.mrwooly357.wool.registry.helper;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.entity.accessory.inventory.AccessoryInventoryUnit;
import net.mrwooly357.wool.entity.accessory.inventory.EntityTypeAccessoryInventoryManager;
import net.mrwooly357.wool.registry.WoolRegistries;

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
        EntityTypeAccessoryInventoryManager.getUnitOrder().get(WoolRegistries.PLAYER_ACCESSORY_INVENTORY).add(id);

        return Registry.register(WoolRegistries.PLAYER_ACCESSORY_INVENTORY, id, unit);
    }
}
