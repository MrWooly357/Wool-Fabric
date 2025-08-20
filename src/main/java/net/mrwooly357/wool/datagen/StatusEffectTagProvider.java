package net.mrwooly357.wool.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.*;

import java.util.concurrent.CompletableFuture;

public abstract class StatusEffectTagProvider extends FabricTagProvider<StatusEffect> {

    public StatusEffectTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.STATUS_EFFECT, registriesFuture);
    }


    @Override
    protected RegistryKey<StatusEffect> reverseLookup(StatusEffect statusEffect) {
        return Registries.STATUS_EFFECT.getKey(statusEffect).orElseThrow();
    }
}
