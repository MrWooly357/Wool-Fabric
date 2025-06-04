package net.mrwooly357.wool.block.util;

import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public interface MultiblockConstructionBuilding {


    default void tickBuilder(BlockPos startPos, BlockPos endPos) {
        createBuilder().tickTimer();
        createBuilder().start();

        if ((createBuilder().getTimer() == createBuilder().getDelay() || createBuilder().getDelay() == 0) && !createBuilder().isSuccessful) {
            createBuilder().tryBuild(startPos, endPos);
            System.out.println(1);

            if (createBuilder().isSuccessful) {
                onSuccess(createBuilder());
                System.out.println(2);
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
