package net.mrwooly357.wool.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.block.util.MultiblockConstructionBlueprint;
import net.mrwooly357.wool.block.util.MultiblockConstructionBlueprints;
import net.mrwooly357.wool.config.custom.WoolConfig;

public class ModRegistries {

    public static final Registry<MultiblockConstructionBlueprint> MULTIBLOCK_CONSTRUCTION_BLUEPRINT = create(
            ModRegistryKeys.MULTIBLOCK_CONSTRUCTION_BLUEPRINT, registry -> MultiblockConstructionBlueprints.EMPTY
    );


    private static <Type> Registry<Type> create(RegistryKey<? extends Registry<Type>> key, Registries.Initializer<Type> initializer) {
        return Registries.create(key, initializer);
    }

    public static void initialize() {
        if (WoolConfig.developerMode) Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " registries");
    }
}
