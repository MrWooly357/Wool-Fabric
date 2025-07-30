package net.mrwooly357.wool.custom_biome.region;

import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.helper.RegionTypeRegistryHelper;

public final class VanillaRegionTypes {

    public static final RegionType OVERWORLD = register(
            "overworld", new RegionType()
    );
    public static final RegionType THE_NETHER = register(
            "the_nether", new RegionType()
    );
    public static final RegionType THE_END = register(
            "the_end", new RegionType()
    );


    private static RegionType register(String name, RegionType regionType) {
        return RegionTypeRegistryHelper.register(Identifier.ofVanilla(name), regionType);
    }

    public static void initialize() {
        if (WoolConfig.enableDeveloperMode)
            Wool.LOGGER.info("Initializing vanilla region types");
    }
}
