package net.mrwooly357.wool.registry.helper;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public interface FeatureRegistryHelper {


    static <FC extends FeatureConfig> Feature<FC> register(Identifier id, Feature<FC> feature) {
        return Registry.register(Registries.FEATURE, id, feature);
    }
}
