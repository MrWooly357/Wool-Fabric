package net.mrwooly357.wool.block.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public interface MultiblockConstructionProvider {


    default void tryBuild(World world, BlockPos startPos, BlockPos endPos, Direction direction) {
        MultiblockConstructionBuilder builder = new MultiblockConstructionBuilder(getBlueprint(), world);

        if (builder.tryBuild(startPos, endPos, direction)) {
            onSuccess();
        } else
            onFail();
    }

    void onSuccess();

    void onFail();

    MultiblockConstructionBlueprint getBlueprint();
}
