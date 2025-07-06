package net.mrwooly357.wool.registry.helper;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.block.util.MultiblockConstructionBlueprint;
import net.mrwooly357.wool.registry.WoolRegistries;

/**
 * A helper used for registering custom {@link MultiblockConstructionBlueprint}s.
 */
public interface MultiblockConstructionBlueprintRegistryHelper {


    /**
     * Registers a custom {@link MultiblockConstructionBlueprint}.
     * @param id the {@link Identifier}.
     * @param blueprint the {@link MultiblockConstructionBlueprint}.
     * @return a registered {@link MultiblockConstructionBlueprint}.
     */
    static MultiblockConstructionBlueprint register(Identifier id, MultiblockConstructionBlueprint blueprint) {
        return Registry.register(WoolRegistries.MULTIBLOCK_CONSTRUCTION_BLUEPRINT, id, blueprint);
    }
}
