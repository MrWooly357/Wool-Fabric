package net.mrwooly357.wool.block.util;

/**
 * Represents a thing that holds a {@link MultiblockConstructionBlueprint}
 */
public interface MultiblockConstructionBlueprintHolder {


    /**
     * A {@link MultiblockConstructionBlueprint} is stored in this method.
     * @return the {@link MultiblockConstructionBlueprint} stored in this thing.
     */
    MultiblockConstructionBlueprint getBlueprint();
}
