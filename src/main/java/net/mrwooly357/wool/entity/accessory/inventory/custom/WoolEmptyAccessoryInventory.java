package net.mrwooly357.wool.entity.accessory.inventory.custom;

import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.entity.accessory.inventory.AccessoryInventoryUnit;
import net.mrwooly357.wool.screen.slot.custom.accessory.WoolAccessorySlotTypes;
import net.mrwooly357.wool.registry.helper.EmptyAccessoryInventoryRegistryHelper;

public class WoolEmptyAccessoryInventory {

    public static final AccessoryInventoryUnit EMPTY = register(
            "empty", new AccessoryInventoryUnit(
                    WoolAccessorySlotTypes.EMPTY, false
            )
    );


    public static AccessoryInventoryUnit register(String name, AccessoryInventoryUnit unit) {
        return EmptyAccessoryInventoryRegistryHelper.register(Identifier.of(Wool.MOD_ID, name), unit);
    }

    public static void initialize() {
        if (WoolConfig.developerMode)
            Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " empty accessory inventory");
    }
}
