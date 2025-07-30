package net.mrwooly357.wool.registry.helper;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.custom_biome.region.RegionType;
import net.mrwooly357.wool.registry.WoolRegistries;

public interface RegionTypeRegistryHelper {


    static RegionType register(Identifier id, RegionType regionType) {
        return Registry.register(WoolRegistries.REGION_TYPE, id, regionType);
    }
}
