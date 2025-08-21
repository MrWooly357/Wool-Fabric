package net.mrwooly357.wool.custom_biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.*;
import java.util.function.Consumer;

public interface CustomBiomeCreator {


    Overworld getOverworld();


    interface Overworld {

        Set<RegistryKey<Biome>> CAVES = new HashSet<>();


        void createOceanBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters);

        void createLandBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters);

        void createCaveBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters);

        static void writeBiomeParameters(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, BiomeParameters biomeParameters) {
            MultiNoiseUtil.ParameterRange temperature = biomeParameters.getTemperature();
            MultiNoiseUtil.ParameterRange humidity = biomeParameters.getHumidity();
            MultiNoiseUtil.ParameterRange continentalness = biomeParameters.getContinentalness();
            MultiNoiseUtil.ParameterRange erosion = biomeParameters.getErosion();
            MultiNoiseUtil.ParameterRange depth = biomeParameters.getDepth();
            MultiNoiseUtil.ParameterRange weirdness = biomeParameters.getWeirdness();
            float offset = biomeParameters.getOffset();
            RegistryKey<Biome> key = biomeParameters.getKey();

            parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, depth, weirdness, offset), key));
            parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, depth, weirdness, offset), key));
        }

        static void writeCaveBiomeParameters(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, BiomeParameters biomeParameters) {
            RegistryKey<Biome> key = biomeParameters.getKey();

            CAVES.add(key);
            parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(
                    biomeParameters.getTemperature(),
                    biomeParameters.getHumidity(),
                    biomeParameters.getContinentalness(),
                    biomeParameters.getErosion(),
                    biomeParameters.getDepth(),
                    biomeParameters.getWeirdness(),
                    biomeParameters.getOffset()), key));
        }


        enum VanillaDepths {

            REGULAR_0(0.0F),
            REGULAR_1(1.0F),
            CAVE(0.2F, 0.9F),
            DEEP_DARK(1.1F);

            private final MultiNoiseUtil.ParameterRange depth;

            VanillaDepths(float min, float max) {
                this.depth = MultiNoiseUtil.ParameterRange.of(min, max);
            }

            VanillaDepths(float value) {
                this(value, value);
            }


            public MultiNoiseUtil.ParameterRange getDepth() {
                return depth;
            }
        }
    }


    final class Manager {

        private static final List<CustomBiomeCreator> CREATORS = new ArrayList<>();


        public static void register(CustomBiomeCreator creator) {
            CREATORS.add(creator);
        }

        public static void forEach(Consumer<CustomBiomeCreator> action) {
            CREATORS.forEach(action);
        }
    }
}
