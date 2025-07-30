package net.mrwooly357.wool.mixin;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSupplier;
import net.minecraft.world.chunk.BelowZeroRetrogen;
import net.minecraft.world.chunk.Chunk;
import net.mrwooly357.wool.custom_biome.CustomBiomeCreator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(BelowZeroRetrogen.class)
public abstract class CustomCaveBiomesBelowZeroRetrogenAdderMixin {

    @Shadow
    @Final
    private static Set<RegistryKey<Biome>> CAVE_BIOMES;


    @Inject(method = "getBiomeSupplier", at = @At("HEAD"), cancellable = true)
    private static void injectGetBiomeSupplier(BiomeSupplier biomeSupplier, Chunk chunk, CallbackInfoReturnable<BiomeSupplier> cir) {
        if (chunk.hasBelowZeroRetrogen()) {
            cir.setReturnValue((x, y, z, noise) -> {
                RegistryEntry<Biome> registryEntry = biomeSupplier.getBiome(x, y, z, noise);

                return registryEntry.matches(CAVE_BIOMES::contains) || registryEntry.matches(CustomBiomeCreator.Overworld.CAVES::contains) ? registryEntry : chunk.getBiomeForNoiseGen(x, 0, z);
            });
        }
    }
}
