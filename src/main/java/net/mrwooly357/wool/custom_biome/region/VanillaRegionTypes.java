package net.mrwooly357.wool.custom_biome.region;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.registry.WoolRegistries;

public final class VanillaRegionTypes {

    public static final RegionType OVERWORLD = register("overworld", new RegionType());


    private static RegionType register(String name, RegionType regionType) {
        return Registry.register(WoolRegistries.REGION_TYPE, Identifier.ofVanilla(name), regionType);
    }

    public static void initialize() {
            Wool.logInitializing("vanilla region types");
    }
}
