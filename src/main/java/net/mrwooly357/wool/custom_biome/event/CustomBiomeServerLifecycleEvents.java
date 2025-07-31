package net.mrwooly357.wool.custom_biome.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.mrwooly357.wool.custom_biome.region.RegionType;
import net.mrwooly357.wool.custom_biome.region.RegionTypeHolder;
import org.jetbrains.annotations.Nullable;

public class CustomBiomeServerLifecycleEvents {


    public static void initialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            Registry<DimensionOptions> dimensionRegistry = server.getRegistryManager().get(RegistryKeys.DIMENSION);

            for (RegistryEntry<DimensionOptions> entry : dimensionRegistry.getIndexedEntries()) {

                if (entry.value().chunkGenerator() instanceof NoiseChunkGenerator noiseChunkGenerator) {
                    RegionType regionType = getRegionTypeByKey(entry.getKey().orElse(null));

                    if (regionType != null)
                        ((RegionTypeHolder) (Object) noiseChunkGenerator.getSettings().value()).setRegionType(regionType);
                }
            }
        });
    }

    private static @Nullable RegionType getRegionTypeByKey(@Nullable RegistryKey<DimensionOptions> key) {
        if (key != null)
            return RegionType.getById(key.getValue());

        return null;
    }
}
