package net.mrwooly357.wool.entity.util;

import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.PlayerAccessoryInventoryRegistryHelper;
import net.mrwooly357.wool.screen.slot.custom.accessory.WoolAccessorySlotTypes;

public class WoolPlayerAccessoryInventory {

    public static final AccessoryInventoryUnit EMPTY = register(
            "empty", new AccessoryInventoryUnit(
                    WoolAccessorySlotTypes.EMPTY
            )
    );
    public static final AccessoryInventoryUnit HEAD = register(
            "head", new AccessoryInventoryUnit(
                    WoolAccessorySlotTypes.HEAD
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
