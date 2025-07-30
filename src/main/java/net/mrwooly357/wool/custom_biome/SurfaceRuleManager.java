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
        MaterialRules.MaterialRule defaultRule = getSurfaceMaterialRulesByRegionType(regionType);
        MaterialRules.MaterialRule materialRule = null;

        for (MaterialRules.MaterialRule rule : CUSTOM_RULES) {

            if (materialRule != null) {
                materialRule = MaterialRules.sequence(rule, materialRule);
            } else
                materialRule = rule;
        }

        if (defaultRule != null && materialRule != null)
            return MaterialRules.sequence(materialRule, defaultRule);

        return materialRule;
    }

    static @Nullable MaterialRules.MaterialRule getSurfaceMaterialRulesByRegionType(RegionType regionType) {
        return REGION_TYPE_TO_SURFACE_MATERIAL_RULE.get(regionType);
    }
}
