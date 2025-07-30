package net.mrwooly357.wool.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.SharedConstants;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.VanillaBiomeParameters;
import net.mrwooly357.wool.custom_biome.CustomBiomeCreator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(VanillaBiomeParameters.class)
public abstract class OverworldCustomBiomeAdderMixin {


    @Inject(method = "writeOverworldBiomeParameters", at = @At("TAIL"))
    private void injectWriteOverworldBiomeParameters(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, CallbackInfo ci) {
        if (!SharedConstants.DEBUG_BIOME_SOURCE) {
            CustomBiomeCreator.CREATORS.forEach(creator -> {
                CustomBiomeCreator.Overworld overworld = creator.getOverworld();

                overworld.createCustomOceanBiomes(parameters);
                overworld.createCustomLandBiomes(parameters);
                overworld.createCustomCaveBiomes(parameters);
            });
        }
    }
}
