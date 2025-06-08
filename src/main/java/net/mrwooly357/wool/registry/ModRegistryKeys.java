package net.mrwooly357.wool.registry;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.block.util.MultiblockConstructionBlueprint;
import net.mrwooly357.wool.config.custom.WoolConfig;

public class ModRegistryKeys {

    public static final RegistryKey<Registry<MultiblockConstructionBlueprint>> MULTIBLOCK_CONSTRUCTION_BLUEPRINT = of("multiblock_construction_blueprint");


    private static <Type> RegistryKey<Registry<Type>> of(String name) {
        return of(Identifier.of(Wool.MOD_ID, name));
    }

    public static <Type> RegistryKey<Registry<Type>> of(Identifier id) {
        return RegistryKey.ofRegistry(id);
    }

    public static void initialize() {
        if (WoolConfig.developerMode) Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " registry keys");
    }
}
