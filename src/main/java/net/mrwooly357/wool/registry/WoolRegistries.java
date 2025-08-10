package net.mrwooly357.wool.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.multiblock_construction.MultiblockConstructionBlueprint;
import net.mrwooly357.wool.multiblock_construction.WoolMultiblockConstructionBlueprints;
import net.mrwooly357.wool.config.Config;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.accessory.entity.inventory.AccessoryInventoryUnit;
import net.mrwooly357.wool.accessory.entity.inventory.WoolEntityAccessoryInventories;
import net.mrwooly357.wool.accessory.entity.inventory.custom.WoolPlayerAccessoryInventory;
import net.mrwooly357.wool.animation.interpolation.Interpolation;
import net.mrwooly357.wool.animation.interpolation.WoolInterpolations;
import net.mrwooly357.wool.accessory.screen.slot.custom.AccessorySlotType;
import net.mrwooly357.wool.accessory.screen.slot.WoolAccessorySlotTypes;
import net.mrwooly357.wool.custom_biome.region.RegionType;
import net.mrwooly357.wool.custom_biome.region.VanillaRegionTypes;

public class WoolRegistries {

    public static final Registry<Config> CONFIG = create(WoolRegistryKeys.CONFIG, registry -> Wool.CONFIG);
    public static final Registry<MultiblockConstructionBlueprint> MULTIBLOCK_CONSTRUCTION_BLUEPRINT = create(WoolRegistryKeys.MULTIBLOCK_CONSTRUCTION_BLUEPRINT,
            registry -> WoolMultiblockConstructionBlueprints.EMPTY);
    public static final Registry<AccessorySlotType> ACCESSORY_SLOT_TYPE = create(WoolRegistryKeys.ACCESSORY_SLOT_TYPE, registry -> WoolAccessorySlotTypes.BELT);
    public static final Registry<Registry<AccessoryInventoryUnit>> ENTITY_ACCESSORY_INVENTORY = create(WoolRegistryKeys.ENTITY_TYPE_ACCESSORY_INVENTORY, registry -> WoolEntityAccessoryInventories.PLAYER);
    public static final Registry<AccessoryInventoryUnit> PLAYER_ACCESSORY_INVENTORY = create(WoolRegistryKeys.PLAYER_ACCESSORY_INVENTORY, registry -> WoolPlayerAccessoryInventory.BELT);
    public static final Registry<Interpolation> INTERPOLATION = create(WoolRegistryKeys.INTERPOLATION, registry -> WoolInterpolations.LINEAR);
    public static final Registry<RegionType> REGION_TYPE = create(WoolRegistryKeys.REGION_TYPE, registry -> VanillaRegionTypes.OVERWORLD);


    private static <T> Registry<T> create(RegistryKey<? extends Registry<T>> key, Registries.Initializer<T> initializer) {
        return Registries.create(key, initializer);
    }

    public static void initialize() {
        if (WoolConfig.enableDeveloperMode)
            Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " registries");
    }
}
