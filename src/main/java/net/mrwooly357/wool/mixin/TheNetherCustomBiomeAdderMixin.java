package net.mrwooly357.wool.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.mrwooly357.wool.custom_biome.CustomBiomeCreator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(targets = "net/minecraft/world/biome/source/MultiNoiseBiomeSourceParameterList$Preset$1")
public abstract class TheNetherCustomBiomeAdderMixin {


    @Inject(method = "apply", at = @At("RETURN"), cancellable = true)
    private void injectApply(Function<RegistryKey<Biome>, ?> function, CallbackInfoReturnable<MultiNoiseUtil.Entries<?>> cir) {
        cir.setReturnValue(getTheNetherEntries(function));
    }

    @Unique
    private static <T> MultiNoiseUtil.Entries<T> getTheNetherEntries(Function<RegistryKey<Biome>, T> biomeEntryGetter) {
        ImmutableList.Builder<Pair<MultiNoiseUtil.NoiseHypercube, T>> builder = ImmutableList.builder();
        new CustomBiomeCreator.TheNether.BiomeParametersHelper().writeTheNetherBiomeParameters(pair -> builder.add(pair.mapSecond(biomeEntryGetter)));

        return new MultiNoiseUtil.Entries<>(builder.build());
    }
}
