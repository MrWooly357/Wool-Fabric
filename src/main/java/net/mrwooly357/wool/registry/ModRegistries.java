package net.mrwooly357.wool.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.animation.*;
import net.mrwooly357.wool.animation.condition.Condition;
import net.mrwooly357.wool.animation.condition.Conditions;
import net.mrwooly357.wool.animation.interpolation.Interpolation;
import net.mrwooly357.wool.animation.interpolation.Interpolations;
import net.mrwooly357.wool.block.util.MultiblockConstructionBlueprint;
import net.mrwooly357.wool.block.util.MultiblockConstructionBlueprints;
import net.mrwooly357.wool.config.custom.WoolConfig;

public class ModRegistries {

    public static final Registry<MultiblockConstructionBlueprint> MULTIBLOCK_CONSTRUCTION_BLUEPRINT = create(
            ModRegistryKeys.MULTIBLOCK_CONSTRUCTION_BLUEPRINT, registry -> MultiblockConstructionBlueprints.EMPTY
    );
    public static final Registry<Animation> ANIMATION = create(
            ModRegistryKeys.ANIMATION, registry -> Animations.EMPTY
    );
    public static final Registry<Condition> CONDITION = create(
            ModRegistryKeys.CONDITION, registry -> Conditions.EMPTY
    );
    public static final Registry<Interpolation> INTERPOLATION = create(
            ModRegistryKeys.INTERPOLATION, registry -> Interpolations.LINEAR
    );


    private static <Type> Registry<Type> create(RegistryKey<? extends Registry<Type>> key, Registries.Initializer<Type> initializer) {
        return Registries.create(key, initializer);
    }

    public static void initialize() {
        if (WoolConfig.developerMode) Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " registries");
    }
}
