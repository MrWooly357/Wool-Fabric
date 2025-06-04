package net.mrwooly357.wool.block.util;

import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public interface MultiblockConstructionBuilding {


    default void tickBuilder(BlockPos startPos, BlockPos endPos) {
        if ((createBuilder().getTimer() == createBuilder().getDelay() || createBuilder().getDelay() == 0) && !createBuilder().isSuccessful) {
            System.out.println(1);
            createBuilder().tryBuild(startPos, endPos);

            if (createBuilder().isSuccessful) {
                onSuccess(createBuilder());
                System.out.println(2);
            }
        }

        createBuilder().tickTimer();
    }

    int getDelay();

    void onSuccess(MultiblockConstructionBuilder builder);

    @NotNull MultiblockConstructionBuilder getBuilder();

    default MultiblockConstructionBuilder createBuilder() {
        getBuilder().setDelay(getDelay());

        return getBuilder();
    }
}
