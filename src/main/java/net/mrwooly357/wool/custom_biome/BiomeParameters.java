package net.mrwooly357.wool.custom_biome;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

public final class BiomeParameters {

    private final MultiNoiseUtil.ParameterRange temperature;
    private final MultiNoiseUtil.ParameterRange humidity;
    private final MultiNoiseUtil.ParameterRange continentalness;
    private final MultiNoiseUtil.ParameterRange erosion;
    private final MultiNoiseUtil.ParameterRange depth;
    private final MultiNoiseUtil.ParameterRange weirdness;
    private final float offset;
    private final RegistryKey<Biome> key;

    private BiomeParameters(MultiNoiseUtil.ParameterRange temperature,
                            MultiNoiseUtil.ParameterRange humidity,
                            MultiNoiseUtil.ParameterRange continentalness,
                            MultiNoiseUtil.ParameterRange erosion,
                            MultiNoiseUtil.ParameterRange depth,
                            MultiNoiseUtil.ParameterRange weirdness,
                            float offset,
                            RegistryKey<Biome> key) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.continentalness = continentalness;
        this.erosion = erosion;
        this.depth = depth;
        this.weirdness = weirdness;
        this.offset = offset;
        this.key = key;
    }


    public MultiNoiseUtil.ParameterRange getTemperature() {
        return temperature;
    }

    public MultiNoiseUtil.ParameterRange getHumidity() {
        return humidity;
    }

    public MultiNoiseUtil.ParameterRange getContinentalness() {
        return continentalness;
    }

    public MultiNoiseUtil.ParameterRange getErosion() {
        return erosion;
    }

    public MultiNoiseUtil.ParameterRange getDepth() {
        return depth;
    }

    public MultiNoiseUtil.ParameterRange getWeirdness() {
        return weirdness;
    }

    public float getOffset() {
        return offset;
    }

    public RegistryKey<Biome> getKey() {
        return key;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private MultiNoiseUtil.ParameterRange temperature;
        private MultiNoiseUtil.ParameterRange humidity;
        private MultiNoiseUtil.ParameterRange continentalness;
        private MultiNoiseUtil.ParameterRange erosion;
        private MultiNoiseUtil.ParameterRange depth;
        private MultiNoiseUtil.ParameterRange weirdness;
        private float offset;

        private Builder() {
            temperature = MultiNoiseUtil.ParameterRange.of(0.0F, 0.0F);
            humidity = MultiNoiseUtil.ParameterRange.of(0.0F, 0.0F);
            continentalness = MultiNoiseUtil.ParameterRange.of(0.0F, 0.0F);
            erosion = MultiNoiseUtil.ParameterRange.of(0.0F, 0.0F);
            depth = MultiNoiseUtil.ParameterRange.of(0.0F, 0.0F);
            weirdness = MultiNoiseUtil.ParameterRange.of(0.0F, 0.0F);
        }


        public Builder temperature(float min, float max) {
            temperature = MultiNoiseUtil.ParameterRange.of(min, max);

            return this;
        }

        public Builder temperature(float value) {
            return temperature(value, value);
        }

        public Builder humidity(float min, float max) {
            humidity = MultiNoiseUtil.ParameterRange.of(min, max);

            return this;
        }

        public Builder humidity(float value) {
           return humidity(value, value);
        }

        public Builder continentalness(float min, float max) {
            continentalness = MultiNoiseUtil.ParameterRange.of(min, max);

            return this;
        }

        public Builder continentalness(float value) {
            return continentalness(value, value);
        }

        public Builder erosion(float min, float max) {
            erosion = MultiNoiseUtil.ParameterRange.of(min, max);

            return this;
        }

        public Builder erosion(float value) {
            return erosion(value, value);
        }

        public Builder depth(float min, float max) {
            depth = MultiNoiseUtil.ParameterRange.of(min, max);

            return this;
        }

        public Builder depth(float value) {
            return depth(value, value);
        }

        public Builder weirdness(float min, float max) {
            weirdness = MultiNoiseUtil.ParameterRange.of(min, max);

            return this;
        }

        public Builder weirdness(float value) {
            return weirdness(value, value);
        }

        public Builder offset(float offset) {
            this.offset = offset;

            return this;
        }

        public BiomeParameters build(RegistryKey<Biome> key) {
            return new BiomeParameters(temperature, humidity, continentalness, erosion, depth, weirdness, offset, key);
        }
    }
}
