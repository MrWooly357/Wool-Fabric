package net.mrwooly357.wool.animation.condition;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.animation.condition.custom.EmptyCondition;
import net.mrwooly357.wool.animation.condition.custom.IsBabyCondition;
import net.mrwooly357.wool.registry.ModRegistries;
import net.mrwooly357.wool.registry.ModRegistryKeys;

public class Conditions {

    public static final Condition EMPTY = register(
            "empty", new EmptyCondition()
    );

    public static final Condition IS_BABY = register(
            "is_baby", new IsBabyCondition()
    );


    private static Condition register(String name, Condition condition) {
        return register(Identifier.of(Wool.MOD_ID, name), condition);
    }

    public static Condition register(Identifier id, Condition condition) {
        return Registry.register(ModRegistries.CONDITION, RegistryKey.of(ModRegistryKeys.CONDITION, id), condition);
    }
}
