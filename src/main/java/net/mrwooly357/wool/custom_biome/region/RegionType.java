package net.mrwooly357.wool.custom_biome.region;

import net.minecraft.util.Identifier;
import net.mrwooly357.wool.registry.WoolRegistries;
import org.jetbrains.annotations.Nullable;

public final class RegionType {

    public RegionType() {}


    public static @Nullable RegionType getById(Identifier id) {
        return WoolRegistries.REGION_TYPE.get(id);
    }
}
