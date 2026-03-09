package net.mrwooly357.wool.mixin;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.mrwooly357.wool.world.biome.OverworldBiomeData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(targets = "net/minecraft/world/biome/source/MultiNoiseBiomeSourceParameterList$Preset")
public abstract class MultiNoiseBiomeSourceParameterListPresetMixin {


    @Inject(method = "getOverworldEntries", at = @At("RETURN"), cancellable = true)
    private static <T> void injectApply(Function<RegistryKey<Biome>, T> function, CallbackInfoReturnable<MultiNoiseUtil.Entries<T>> cir) {
        cir.setReturnValue(OverworldBiomeData.modifyEntries(cir.getReturnValue(), function));
    }
}
