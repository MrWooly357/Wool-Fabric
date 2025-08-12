package net.mrwooly357.wool.registry;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.block_util.multiblock_construction.MultiblockConstructionBlueprint;
import net.mrwooly357.wool.config.Config;
import net.mrwooly357.wool.accessory.entity.inventory.AccessoryInventoryUnit;
import net.mrwooly357.wool.animation.interpolation.Interpolation;
import net.mrwooly357.wool.accessory.screen.slot.custom.AccessorySlotType;
import net.mrwooly357.wool.custom_biome.region.RegionType;

public class WoolRegistryKeys {

    public static final RegistryKey<Registry<Config>> CONFIG = create("config");
    public static final RegistryKey<Registry<MultiblockConstructionBlueprint>> MULTIBLOCK_CONSTRUCTION_BLUEPRINT = create("multiblock_construction_blueprint");
    public static final RegistryKey<Registry<AccessorySlotType>> ACCESSORY_SLOT_TYPE = create("accessory_slot_type");
    public static final RegistryKey<Registry<Registry<AccessoryInventoryUnit>>> ENTITY_TYPE_ACCESSORY_INVENTORY = create("entity_type_accessory_inventory");
    public static final RegistryKey<Registry<AccessoryInventoryUnit>> PLAYER_ACCESSORY_INVENTORY = create("player_accessory_inventory");
    public static final RegistryKey<Registry<Interpolation>> INTERPOLATION = create("interpolation");
    public static final RegistryKey<Registry<RegionType>> REGION_TYPE = create("region_type");


    private static <T> RegistryKey<Registry<T>> create(String name) {
        return RegistryKey.ofRegistry(Identifier.of(Wool.MOD_ID, name));
    }
}
