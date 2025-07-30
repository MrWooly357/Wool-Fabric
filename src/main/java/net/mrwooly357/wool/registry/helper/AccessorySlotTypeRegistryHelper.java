package net.mrwooly357.wool.registry.helper;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.registry.WoolRegistries;
import net.mrwooly357.wool.accessory.screen.slot.custom.AccessorySlotType;

/**
 * A helper used for registering custom {@link AccessorySlotType}s.
 */
public interface AccessorySlotTypeRegistryHelper {


    /**
     * Registers a custom {@link AccessorySlotType}.
     * @param id the {@link Identifier}.
     * @param type the {@link AccessorySlotType}.
     * @return a registered {@link AccessorySlotType}.
     */
    static AccessorySlotType register(Identifier id, AccessorySlotType type) {
        return Registry.register(WoolRegistries.ACCESSORY_SLOT_TYPE, id, type);
    }
}
