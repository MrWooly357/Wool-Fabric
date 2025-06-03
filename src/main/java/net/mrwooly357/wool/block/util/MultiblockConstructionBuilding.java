package net.mrwooly357.wool.block.util;

import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public interface MultiblockConstructionBuilding {


    default void tickBuilder(BlockPos startPos, BlockPos endPos) {
        createBuilder().tickTimer();

        if (createBuilder().getTimer() == createBuilder().getDelay() && createBuilder().isSuccessful) {
            createBuilder().tryBuild(startPos, endPos);

            if (createBuilder().isSuccessful) {
                onSuccess(createBuilder());
            }
        }
    }

    int getDelay();

    void onSuccess(MultiblockConstructionBuilder builder);

    @NotNull MultiblockConstructionBuilder getBuilder();

    default MultiblockConstructionBuilder createBuilder() {
        getBuilder().setDelay(getDelay());

        return getBuilder();
    }
}
