package net.mrwooly357.wool.accessory.entity.inventory;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.registry.WoolRegistries;

public final class WoolEntityAccessoryInventories {

    public static final Registry<AccessoryInventoryUnit> PLAYER = register("player_accessory_inventory", WoolRegistries.PLAYER_ACCESSORY_INVENTORY);


    private static Registry<AccessoryInventoryUnit> register(String name, Registry<AccessoryInventoryUnit> inventory) {
        return Registry.register(WoolRegistries.ENTITY_ACCESSORY_INVENTORY, Identifier.of(Wool.MOD_ID, name), inventory);
    }

    public static void initialize() {
        Wool.logInitializing("entity accessory inventories");
    }
}
