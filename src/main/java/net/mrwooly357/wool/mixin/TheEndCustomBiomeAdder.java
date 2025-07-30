package net.mrwooly357.wool.mixin;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraft.world.gen.WorldPreset;
import net.minecraft.world.gen.WorldPresets;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.mrwooly357.wool.custom_biome.CustomBiomeCreator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldPresets.Registrar.class)
public abstract class TheEndCustomBiomeAdder {

    @Mutable
    @Shadow
    @Final
    private DimensionOptions endDimensionOptions;
    @Shadow
    @Final
    private RegistryEntryLookup<Biome> biomeLookup;
    @Shadow
    @Final
    private RegistryEntryLookup<ChunkGeneratorSettings> chunkGeneratorSettingsLookup;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectInit(Registerable<WorldPreset> presetRegisterable, CallbackInfo ci) {
        endDimensionOptions = new DimensionOptions(presetRegisterable.getRegistryLookup(RegistryKeys.DIMENSION_TYPE).getOrThrow(DimensionTypes.THE_END),
                new NoiseChunkGenerator(CustomBiomeCreator.TheEnd.ExtendedTheEndBiomeSource.create(biomeLookup), chunkGeneratorSettingsLookup.getOrThrow(ChunkGeneratorSettings.END)));
    }
}
