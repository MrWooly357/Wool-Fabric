package net.mrwooly357.wool.world.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public final class OverworldBiomeData {

    private static final List<Function<Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>>, CustomOverworldBiomeGenerator>> GENERATOR_FACTORIES = new ArrayList<>();

    private OverworldBiomeData() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate OverworldBiomeData!");
    }


    public static void add(Function<Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>>, CustomOverworldBiomeGenerator> generatorFactory) {
        GENERATOR_FACTORIES.add(generatorFactory);
    }

    public static <T> MultiNoiseUtil.Entries<T> modifyEntries(MultiNoiseUtil.Entries<T> entries, Function<RegistryKey<Biome>, T> function) {
        if (GENERATOR_FACTORIES.isEmpty())
            return entries;
        else {
            List<Pair<MultiNoiseUtil.NoiseHypercube, T>> list = new ArrayList<>(entries.getEntries());
            GENERATOR_FACTORIES.forEach(factory -> factory.apply(pair -> list.add(pair.mapSecond(function))).generate());

            return new MultiNoiseUtil.Entries<>(List.copyOf(list));
        }
    }
}
