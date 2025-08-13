package net.mrwooly357.wool.accessory.entity.inventory.custom;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.accessory.entity.inventory.AccessoryInventoryUnit;
import net.mrwooly357.wool.registry.WoolRegistries;
import net.mrwooly357.wool.accessory.screen.slot.WoolAccessorySlotTypes;

public class WoolPlayerAccessoryInventory {

    public static final AccessoryInventoryUnit BELT = register("belt", new AccessoryInventoryUnit(WoolAccessorySlotTypes.BELT, true));
    public static final AccessoryInventoryUnit RING = register("ring", new AccessoryInventoryUnit(WoolAccessorySlotTypes.RING, true));
    public static final AccessoryInventoryUnit NECKLACE = register("necklace", new AccessoryInventoryUnit(WoolAccessorySlotTypes.NECKLACE, true));


    private static AccessoryInventoryUnit register(String name, AccessoryInventoryUnit unit) {
        return Registry.register(WoolRegistries.PLAYER_ACCESSORY_INVENTORY, Identifier.of(Wool.MOD_ID, name), unit);
    }

    public static void initialize() {
        Wool.logInitializing("player accessory inventory");
    }
}
