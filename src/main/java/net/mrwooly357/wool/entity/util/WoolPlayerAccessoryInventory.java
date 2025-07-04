package net.mrwooly357.wool.entity.util;

import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.PlayerAccessoryInventoryRegistryHelper;
import net.mrwooly357.wool.screen.slot.custom.accessory.WoolAccessorySlotTypes;

public class WoolPlayerAccessoryInventory {

    public static final AccessoryInventoryUnit EMPTY = register(
            "empty", new AccessoryInventoryUnit(
                    WoolAccessorySlotTypes.EMPTY, false
            )
    );
    public static final AccessoryInventoryUnit NECKLACE = register(
            "necklace", new AccessoryInventoryUnit(
                    WoolAccessorySlotTypes.NECKLACE, true
            )
    );
    public static final AccessoryInventoryUnit RING = register(
            "ring", new AccessoryInventoryUnit(
                    WoolAccessorySlotTypes.RING, true
            )
    );
    public static final AccessoryInventoryUnit BELT = register(
            "belt", new AccessoryInventoryUnit(
                    WoolAccessorySlotTypes.BELT, true
            )
    );


    private static AccessoryInventoryUnit register(String name, AccessoryInventoryUnit unit) {
        return PlayerAccessoryInventoryRegistryHelper.register(Identifier.of(Wool.MOD_ID, name), unit);
    }

    public static void initialize() {
        if (WoolConfig.developerMode)
            Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " player accessory inventory");
    }
}
