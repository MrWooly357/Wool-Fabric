package net.mrwooly357.wool.custom_biome;

import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.mrwooly357.wool.custom_biome.region.RegionType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SurfaceRuleManager {

    private static final List<MaterialRules.MaterialRule> CUSTOM_RULES = new ArrayList<>();
    private static final Map<RegionType, MaterialRules.MaterialRule> REGION_TYPE_TO_MATERIAL_RULE = new HashMap<>();


    public static void addRegionType(RegionType regionType, @Nullable MaterialRules.MaterialRule materialRule) {
        REGION_TYPE_TO_MATERIAL_RULE.put(regionType, materialRule);
    }

    public static void addRule(MaterialRules.MaterialRule materialRule) {
        CUSTOM_RULES.add(materialRule);
    }

    @Nullable
    public static MaterialRules.MaterialRule getCombinedSurfaceMaterialRules(RegionType regionType) {
        MaterialRules.MaterialRule defaultRule = REGION_TYPE_TO_MATERIAL_RULE.get(regionType);
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
