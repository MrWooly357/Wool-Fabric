package net.mrwooly357.wool.registry;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.animation.Animation;
import net.mrwooly357.wool.animation.condition.Condition;
import net.mrwooly357.wool.animation.interpolation.Interpolation;

public class ModRegistryKeys extends RegistryKeys {

    public static final RegistryKey<Registry<Animation>> ANIMATION = of("animation");
    public static final RegistryKey<Registry<Condition>> CONDITION = of("condition");
    public static final RegistryKey<Registry<Interpolation>> INTERPOLATION = of("interpolation");


    private static <Type> RegistryKey<Registry<Type>> of(String name) {
        return RegistryKey.ofRegistry(Identifier.of(Wool.MOD_ID, name));
    }

    protected static <Type> RegistryKey<Registry<Type>> of(String modId, String name) {
        return RegistryKey.ofRegistry(Identifier.of(modId, name));
    }

    public static void init() {
        Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " registry keys");
    }
}
