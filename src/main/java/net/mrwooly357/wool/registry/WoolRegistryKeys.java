package net.mrwooly357.wool.registry;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.block.util.MultiblockConstructionBlueprint;
import net.mrwooly357.wool.config.Config;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.entity.util.AccessoryInventoryUnit;
import net.mrwooly357.wool.screen.slot.custom.accessory.AccessorySlotType;

public class WoolRegistryKeys {

    public static final RegistryKey<Registry<Config>> CONFIG = create(
            "config"
    );
    public static final RegistryKey<Registry<MultiblockConstructionBlueprint>> MULTIBLOCK_CONSTRUCTION_BLUEPRINT = create(
            "multiblock_construction_blueprint"
    );
    public static final RegistryKey<Registry<AccessorySlotType>> ACCESSORY_SLOT_TYPE = create(
            "accessory_slot_type"
    );
    public static final RegistryKey<Registry<Registry<AccessoryInventoryUnit>>> ENTITY_TYPE_ACCESSORY_INVENTORY = create(
            "entity_type_accessory_inventory"
    );
    public static final RegistryKey<Registry<AccessoryInventoryUnit>> EMPTY_ACCESSORY_INVENTORY = create(
            "empty_accessory_inventory"
    );
    public static final RegistryKey<Registry<AccessoryInventoryUnit>> PLAYER_ACCESSORY_INVENTORY = create(
            "player_accessory_inventory"
    );


    private static <T> RegistryKey<Registry<T>> create(String name) {
        return RegistryKey.ofRegistry(Identifier.of(Wool.MOD_ID, name));
    }

    public static void initialize() {
        if (WoolConfig.developerMode)
            Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " registry keys");
    }
}
