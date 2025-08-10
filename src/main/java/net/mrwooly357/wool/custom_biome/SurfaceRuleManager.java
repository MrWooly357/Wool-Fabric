package net.mrwooly357.wool.custom_biome;

import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.mrwooly357.wool.custom_biome.region.RegionType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SurfaceRuleManager {

    List<MaterialRules.MaterialRule> CUSTOM_RULES = new ArrayList<>();
    Map<RegionType, MaterialRules.@Nullable MaterialRule> REGION_TYPE_TO_SURFACE_MATERIAL_RULE = new HashMap<>();


    static void addCustomRule(MaterialRules.MaterialRule materialRule) {
        CUSTOM_RULES.add(materialRule);
    }

    static void addRegionType(RegionType regionType, @Nullable MaterialRules.MaterialRule materialRule) {
        REGION_TYPE_TO_SURFACE_MATERIAL_RULE.put(regionType, materialRule);
    }

    static @Nullable MaterialRules.MaterialRule getCombinedSurfaceMaterialRules(RegionType regionType) {
        MaterialRules.MaterialRule defaultRule = REGION_TYPE_TO_SURFACE_MATERIAL_RULE.get(regionType);
        MaterialRules.MaterialRule customRule = null;

        for (MaterialRules.MaterialRule rule : CUSTOM_RULES) {

            if (customRule != null) {
                customRule = MaterialRules.sequence(rule, customRule);
            } else
                customRule = rule;
        }

        if (defaultRule != null && customRule != null)
            return MaterialRules.sequence(customRule, defaultRule);

        return defaultRule;
    }
}
