package net.mrwooly357.wool.world.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.function.Consumer;

public abstract class CustomOverworldBiomeGenerator {

    private final Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters;
    protected static final MultiNoiseUtil.ParameterRange DEFAULT_PARAMETER = parameter(-1.0F, 1.0F);

    protected CustomOverworldBiomeGenerator(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters) {
        this.parameters = parameters;
    }


    public abstract void generate();

    protected void writeBiome(
            MultiNoiseUtil.ParameterRange temperature,
            MultiNoiseUtil.ParameterRange humidity,
            MultiNoiseUtil.ParameterRange continentalness,
            MultiNoiseUtil.ParameterRange erosion,
            MultiNoiseUtil.ParameterRange weirdness,
            float offset,
            RegistryKey<Biome> biome
    ) {
        parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, MultiNoiseUtil.ParameterRange.of(0.0F), weirdness, offset), biome));
        parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, MultiNoiseUtil.ParameterRange.of(1.0F), weirdness, offset), biome));
    }

    protected void writeCaveBiome(
            MultiNoiseUtil.ParameterRange temperature,
            MultiNoiseUtil.ParameterRange humidity,
            MultiNoiseUtil.ParameterRange continentalness,
            MultiNoiseUtil.ParameterRange erosion,
            MultiNoiseUtil.ParameterRange weirdness,
            float offset,
            RegistryKey<Biome> biome
    ) {
        parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, MultiNoiseUtil.ParameterRange.of(0.2F, 0.9F), weirdness, offset), biome));
    }

    protected static MultiNoiseUtil.ParameterRange parameter(float point) {
        return MultiNoiseUtil.ParameterRange.of(point);
    }

    protected static MultiNoiseUtil.ParameterRange parameter(float min, float max) {
        return MultiNoiseUtil.ParameterRange.of(min, max);
    }

    protected static MultiNoiseUtil.ParameterRange combineParameters(MultiNoiseUtil.ParameterRange min, MultiNoiseUtil.ParameterRange max) {
        return MultiNoiseUtil.ParameterRange.combine(min, max);
    }
}
