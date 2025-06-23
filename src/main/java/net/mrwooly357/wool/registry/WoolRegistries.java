package net.mrwooly357.wool.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.block.util.MultiblockConstructionBlueprint;
import net.mrwooly357.wool.block.util.WoolMultiblockConstructionBlueprints;
import net.mrwooly357.wool.config.Config;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.entity.util.AccessoryInventoryUnit;
import net.mrwooly357.wool.entity.util.WoolEmptyAccessoryInventory;
import net.mrwooly357.wool.entity.util.WoolEntityAccessoryInventories;
import net.mrwooly357.wool.entity.util.WoolPlayerAccessoryInventory;
import net.mrwooly357.wool.screen.slot.custom.accessory.AccessorySlotType;
import net.mrwooly357.wool.screen.slot.custom.accessory.WoolAccessorySlotTypes;

public class WoolRegistries {

    public static final Registry<Config> CONFIG = create(
            WoolRegistryKeys.CONFIG, registry -> Wool.CONFIG
    );
    public static final Registry<MultiblockConstructionBlueprint> MULTIBLOCK_CONSTRUCTION_BLUEPRINT = create(
            WoolRegistryKeys.MULTIBLOCK_CONSTRUCTION_BLUEPRINT, registry -> WoolMultiblockConstructionBlueprints.EMPTY
    );
    public static final Registry<AccessorySlotType> ACCESSORY_SLOT_TYPE = create(
            WoolRegistryKeys.ACCESSORY_SLOT_TYPE, registry -> WoolAccessorySlotTypes.EMPTY
    );
    public static final Registry<Registry<AccessoryInventoryUnit>> ENTITY_ACCESSORY_INVENTORY = create(
            WoolRegistryKeys.ENTITY_TYPE_ACCESSORY_INVENTORY, registry -> WoolEntityAccessoryInventories.EMPTY
    );
    public static final Registry<AccessoryInventoryUnit> EMPTY_ACCESSORY_INVENTORY = create(
            WoolRegistryKeys.EMPTY_ACCESSORY_INVENTORY, registry -> WoolEmptyAccessoryInventory.EMPTY
    );
    public static final Registry<AccessoryInventoryUnit> PLAYER_ACCESSORY_INVENTORY = create(
            WoolRegistryKeys.PLAYER_ACCESSORY_INVENTORY, registry -> WoolPlayerAccessoryInventory.EMPTY
    );


    private static <T> Registry<T> create(RegistryKey<? extends Registry<T>> key, Registries.Initializer<T> initializer) {
        return Registries.create(key, initializer);
    }

    public static void initialize() {
        if (WoolConfig.developerMode)
            Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " registries");
    }
}
