package net.mrwooly357.wool.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.animation.*;
import net.mrwooly357.wool.animation.condition.Condition;
import net.mrwooly357.wool.animation.condition.Conditions;
import net.mrwooly357.wool.animation.interpolation.Interpolation;
import net.mrwooly357.wool.animation.interpolation.Interpolations;

public class ModRegistries extends Registries {

    public static final Registry<Animation> ANIMATION = create(
            ModRegistryKeys.ANIMATION, registry -> Animations.EMPTY
    );
    public static final Registry<Condition> CONDITION = create(
            ModRegistryKeys.CONDITION, registry -> Conditions.IS_BABY
    );
    public static final Registry<Interpolation> INTERPOLATION = create(
            ModRegistryKeys.INTERPOLATION, registry -> Interpolations.LINEAR
    );


    public static void init() {
        Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " registries");
    }
}
