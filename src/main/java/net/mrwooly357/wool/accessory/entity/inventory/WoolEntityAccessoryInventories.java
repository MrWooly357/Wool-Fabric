package net.mrwooly357.wool.accessory.entity.inventory;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.helper.EntityAccessoryInventoryRegistryHelper;
import net.mrwooly357.wool.registry.WoolRegistries;

public final class WoolEntityAccessoryInventories {

    public static final Registry<AccessoryInventoryUnit> PLAYER = register("player_accessory_inventory", WoolRegistries.PLAYER_ACCESSORY_INVENTORY);


    private static Registry<AccessoryInventoryUnit> register(String name, Registry<AccessoryInventoryUnit> inventory) {
        return EntityAccessoryInventoryRegistryHelper.register(Identifier.of(Wool.MOD_ID, name), inventory);
    }

    public static void initialize() {
        if (WoolConfig.enableDeveloperMode)
            Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " entity accessory inventories");
    }
}
