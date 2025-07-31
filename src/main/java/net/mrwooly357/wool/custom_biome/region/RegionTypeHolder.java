package net.mrwooly357.wool.custom_biome.region;

import org.jetbrains.annotations.Nullable;

public interface RegionTypeHolder {


    @Nullable
    RegionType getRegionType();

    void setRegionType(@Nullable RegionType regionType);
}
