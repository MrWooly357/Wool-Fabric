package net.mrwooly357.wool.block.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

public interface MultiblockConstructionBuilding {


    default void tickBuilder(BlockPos startPos, BlockPos endPos, Direction direction) {
            System.out.println(1);


            if (getBuilder().tryBuild(startPos, endPos, direction)) {
                onSuccess(getBuilder());
                System.out.println(2);
            }
    }

    void onSuccess(MultiblockConstructionBuilder builder);

    @NotNull MultiblockConstructionBuilder getBuilder();
}
