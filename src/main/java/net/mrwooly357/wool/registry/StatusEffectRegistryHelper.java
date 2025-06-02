package net.mrwooly357.wool.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

/**
 * A helper used for registering custom {@link StatusEffect}s.
 */
public interface StatusEffectRegistryHelper {

    /**
     * Registers a custom {@link StatusEffect}.
     * @param id the {@link Identifier}.
     * @param statusEffect the {@link StatusEffect}.
     * @return a registered status effect.
     */
    static RegistryEntry<StatusEffect> register(Identifier id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, id, statusEffect);
    }
}
