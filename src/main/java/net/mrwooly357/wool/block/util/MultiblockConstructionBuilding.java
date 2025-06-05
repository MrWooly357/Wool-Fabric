package net.mrwooly357.wool.block.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

public interface MultiblockConstructionBuilding {


    default void tryBuild(BlockPos startPos, BlockPos endPos, Direction direction) {
            if (getBuilder().tryBuild(startPos, endPos, direction)) {
                onSuccess();
            } else {
                onFail();
            }
    }

    void onSuccess();

    void onFail();

    @NotNull MultiblockConstructionBuilder getBuilder();
}
