package net.mrwooly357.wool.animation;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.registry.ModRegistries;
import net.mrwooly357.wool.registry.ModRegistryKeys;

import java.util.Map;

public class Animations {

    public static final Animation EMPTY = register(
            "empty", new Animation(
                    Identifier.of(Wool.MOD_ID, "empty"), 0, false, Map.of()
            ));


    private static Animation register(String name, Animation animation) {
        return register(Identifier.of(Wool.MOD_ID, name), animation);
    }

    public static Animation register(Identifier id, Animation animation) {
        return Registry.register(ModRegistries.ANIMATION, RegistryKey.of(ModRegistryKeys.ANIMATION, id), animation);
    }

    public static void initialize() {
        Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " animations");
    }
}
