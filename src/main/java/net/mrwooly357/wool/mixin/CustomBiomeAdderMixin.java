package net.mrwooly357.wool.mixin;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BuiltinBiomes;
import net.mrwooly357.wool.custom_biome.CustomBiomeCreator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BuiltinBiomes.class)
public abstract class CustomBiomeAdderMixin {


    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void injectBootstrap(Registerable<Biome> biomeRegisterable, CallbackInfo ci) {
        CustomBiomeCreator.CREATORS.forEach(creator ->
                creator.createBiomes(biomeRegisterable, biomeRegisterable.getRegistryLookup(RegistryKeys.PLACED_FEATURE), biomeRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER)));
    }
}
