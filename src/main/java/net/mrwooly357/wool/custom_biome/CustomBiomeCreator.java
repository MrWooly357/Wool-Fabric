package net.mrwooly357.wool.custom_biome;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.mrwooly357.wool.util.random.RandomUtil;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface CustomBiomeCreator {

    List<CustomBiomeCreator> CREATORS = new ArrayList<>();


    static void addCreator(CustomBiomeCreator creator) {
        CREATORS.add(creator);
    }

    void createBiomes(Registerable<Biome> biomeRegisterable, RegistryEntryLookup<PlacedFeature> placedFeatureLookup, RegistryEntryLookup<ConfiguredCarver<?>> configuredCarverLookup);

    Overworld getOverworld();

    TheNether getTheNether();


    interface Overworld {

        Set<RegistryKey<Biome>> CAVES = new HashSet<>();


        void createCustomOceanBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters);

        void createCustomLandBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters);

        void createCustomCaveBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters);

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

            private final MultiNoiseUtil.ParameterRange defaultDepth;

            VanillaDepths(float min, float max) {
                this.defaultDepth = MultiNoiseUtil.ParameterRange.of(min, max);
            }

            VanillaDepths(float value) {
                this(value, value);
            }


            public MultiNoiseUtil.ParameterRange getDefaultDepth() {
                return defaultDepth;
            }
        }
    }


    interface TheNether {


        void createCustomBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters);

        static void writeVanillaBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters) {
            writeBiomeParameters(parameters, BiomeParameters.builder()
                    .build(BiomeKeys.NETHER_WASTES));
            writeBiomeParameters(parameters, BiomeParameters.builder()
                    .humidity(0.5F)
                    .offset(0.375F)
                    .build(BiomeKeys.WARPED_FOREST));
            writeBiomeParameters(parameters, BiomeParameters.builder()
                    .temperature(0.4F)
                    .build(BiomeKeys.CRIMSON_FOREST));
            writeBiomeParameters(parameters, BiomeParameters.builder()
                    .humidity(-0.5F)
                    .build(BiomeKeys.SOUL_SAND_VALLEY));
            writeBiomeParameters(parameters, BiomeParameters.builder()
                    .temperature(-0.5F)
                    .offset(0.175F)
                    .build(BiomeKeys.BASALT_DELTAS));
        }

        static void writeBiomeParameters(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, BiomeParameters biomeParameters) {
            parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(
                    biomeParameters.getTemperature(),
                    biomeParameters.getHumidity(),
                    biomeParameters.getContinentalness(),
                    biomeParameters.getErosion(),
                    biomeParameters.getDepth(),
                    biomeParameters.getWeirdness(),
                    biomeParameters.getOffset()), biomeParameters.getKey()));
        }


        final class BiomeParametersHelper {


            public void writeTheNetherBiomeParameters(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters) {
                writeVanillaBiomes(parameters);

                for (CustomBiomeCreator creator : CREATORS)
                    creator.getTheNether().createCustomBiomes(parameters);
            }
        }
    }


    interface TheEnd {

        Map<RegistryKey<Biome>, Float> CENTRE_BIOMES = new HashMap<>();
        Map<RegistryKey<Biome>, Float> HIGHLANDS_BIOMES = new HashMap<>();
        Map<RegistryKey<Biome>, Float> MIDLANDS_BIOMES = new HashMap<>();
        Map<RegistryKey<Biome>, Float> SMALL_ISLANDS_BIOMES = new HashMap<>();
        Map<RegistryKey<Biome>, Float> BARRENS_BIOMES = new HashMap<>();


        static void addCentreBiome(RegistryKey<Biome> biomeKey, float weight) {
            CENTRE_BIOMES.put(biomeKey, weight);
        }

        static void addHighlandsBiome(RegistryKey<Biome> biomeKey, float weight) {
            HIGHLANDS_BIOMES.put(biomeKey, weight);
        }

        static void addMidlandsBiome(RegistryKey<Biome> biomeKey, float weight) {
            MIDLANDS_BIOMES.put(biomeKey, weight);
        }

        static void addSmallIslandsBiome(RegistryKey<Biome> biomeKey, float weight) {
            SMALL_ISLANDS_BIOMES.put(biomeKey, weight);
        }

        static void addBarrensBiome(RegistryKey<Biome> biomeKey, float weight) {
            BARRENS_BIOMES.put(biomeKey, weight);
        }


        class ExtendedTheEndBiomeSource extends BiomeSource {

            public static final Map<RegistryEntry<Biome>, Float> CENTRE_BIOMES = new HashMap<>();
            public static final Map<RegistryEntry<Biome>, Float> HIGHLANDS_BIOMES = new HashMap<>();
            public static final Map<RegistryEntry<Biome>, Float> MIDLANDS_BIOMES = new HashMap<>();
            public static final Map<RegistryEntry<Biome>, Float> SMALL_ISLANDS_BIOMES = new HashMap<>();
            public static final Map<RegistryEntry<Biome>, Float> BARRENS_BIOMES = new HashMap<>();

            public static final MapCodec<ExtendedTheEndBiomeSource> CODEC = RecordCodecBuilder.mapCodec(
                    instance -> instance.group(
                                    RegistryOps.getEntryLookupCodec(RegistryKeys.BIOME)
                            )
                            .apply(instance, instance.stable(ExtendedTheEndBiomeSource::create))
            );

            public static ExtendedTheEndBiomeSource create(RegistryEntryLookup<Biome> biomeLookup) {
                TheEnd.CENTRE_BIOMES.forEach((key, weight) -> CENTRE_BIOMES.put(biomeLookup.getOrThrow(key), weight));
                TheEnd.HIGHLANDS_BIOMES.forEach((key, weight) -> HIGHLANDS_BIOMES.put(biomeLookup.getOrThrow(key), weight));
                TheEnd.MIDLANDS_BIOMES.forEach((key, weight) -> MIDLANDS_BIOMES.put(biomeLookup.getOrThrow(key), weight));
                TheEnd.SMALL_ISLANDS_BIOMES.forEach((key, weight) -> SMALL_ISLANDS_BIOMES.put(biomeLookup.getOrThrow(key), weight));
                TheEnd.BARRENS_BIOMES.forEach((key, weight) -> BARRENS_BIOMES.put(biomeLookup.getOrThrow(key), weight));

                return new ExtendedTheEndBiomeSource(
                        biomeLookup.getOrThrow(BiomeKeys.THE_END),
                        biomeLookup.getOrThrow(BiomeKeys.END_HIGHLANDS),
                        biomeLookup.getOrThrow(BiomeKeys.END_MIDLANDS),
                        biomeLookup.getOrThrow(BiomeKeys.SMALL_END_ISLANDS),
                        biomeLookup.getOrThrow(BiomeKeys.END_BARRENS)
                );
            }

            private ExtendedTheEndBiomeSource(RegistryEntry<Biome> centreBiome, RegistryEntry<Biome> highlandsBiome, RegistryEntry<Biome> midlandsBiome, RegistryEntry<Biome> smallIslandsBiome, RegistryEntry<Biome> barrensBiome) {
                CENTRE_BIOMES.put(centreBiome, 1.0F);
                HIGHLANDS_BIOMES.put(highlandsBiome, 1.0F);
                MIDLANDS_BIOMES.put(midlandsBiome, 1.0F);
                SMALL_ISLANDS_BIOMES.put(smallIslandsBiome, 1.0F);
                BARRENS_BIOMES.put(barrensBiome, 1.0F);
            }


            @Override
            protected Stream<RegistryEntry<Biome>> biomeStream() {
                List<RegistryEntry<Biome>> biomes = new ArrayList<>();

                biomes.addAll(CENTRE_BIOMES.keySet());
                biomes.addAll(HIGHLANDS_BIOMES.keySet());
                biomes.addAll(MIDLANDS_BIOMES.keySet());
                biomes.addAll(SMALL_ISLANDS_BIOMES.keySet());
                biomes.addAll(BARRENS_BIOMES.keySet());

                return biomes.stream();
            }

            @Override
            protected MapCodec<? extends BiomeSource> getCodec() {
                return CODEC;
            }

            @Override
            public RegistryEntry<Biome> getBiome(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise) {
                int i = BiomeCoords.toBlock(x);
                int j = BiomeCoords.toBlock(z);
                int k = ChunkSectionPos.getSectionCoord(i);
                int l = ChunkSectionPos.getSectionCoord(j);

                if ((long) k * k + (long) l * l <= 4096L) {
                    return RandomUtil.weighted(CENTRE_BIOMES);
                } else {
                    double d = noise.erosion().sample(new DensityFunction.UnblendedNoisePos((ChunkSectionPos.getSectionCoord(i) * 2 + 1) * 8, BiomeCoords.toBlock(y), (ChunkSectionPos.getSectionCoord(j) * 2 + 1) * 8));

                    if (d > 0.25) {
                        return RandomUtil.weighted(HIGHLANDS_BIOMES);
                    } else if (d >= -0.0625) {
                        return RandomUtil.weighted(MIDLANDS_BIOMES);
                    } else
                        return d < -0.21875 ? RandomUtil.weighted(SMALL_ISLANDS_BIOMES) : RandomUtil.weighted(BARRENS_BIOMES);
                }
            }
        }
    }
}
