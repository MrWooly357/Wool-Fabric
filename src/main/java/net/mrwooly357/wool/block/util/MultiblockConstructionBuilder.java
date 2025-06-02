package net.mrwooly357.wool.block.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MultiblockConstructionBuilder {

    private MultiblockConstructionBlueprint blueprint;
    private World world;
    private boolean canContinue;
    private int delay;
    private int timer;
    private BlockPos previous;
    public boolean isSuccessful;

    public MultiblockConstructionBuilder(MultiblockConstructionBlueprint blueprint, World world) {
        this.blueprint = blueprint;
        this.world = world;
    }


    public void tryBuild(BlockPos start, BlockPos end) {
        if (canContinue) {
            resetTimer();

            for (int a = 0; a < blueprint.getSizeInLayers(); a++) {
                MultiblockConstructionBlueprint.Layer layer = blueprint.getLayer(a);

                for (int b = 0; b < layer.getSizeInPatterns(); b++) {
                    List<@Nullable BlockState> pattern = layer.getDefinedPattern(b);

                    for (BlockState blockState : pattern) {

                        if (previous == null) {
                            setPrevious(start);
                        }

                        BlockPos blockPosToCheck = getBlockPosToCheck(end, b);
                        BlockState blockStateToCheck = world.getBlockState(blockPosToCheck);

                        if (blockStateToCheck != blockState && blockState != null) {
                            stop();

                            return;
                        } else {
                            setPrevious(blockPosToCheck);
                        }

                        if (blockPosToCheck == end) {
                            stop();

                            isSuccessful = true;

                            return;
                        }
                    }
                }
            }
        }
    }

    private @NotNull BlockPos getBlockPosToCheck(BlockPos end, int b) {
        int x = previous.getX();
        int y = previous.getY() + b;
        int z = previous.getZ();

        if (x < end.getX()) {
            x++;
        } else if (x > end.getX()) {
            x--;
        }

        if (z < end.getZ()) {
            z++;
        } else if (z > end.getZ()) {
            z--;
        }

        return new BlockPos(x, y, z);
    }

    public void start() {
        canContinue = true;

        resetTimer();
    }

    public void stop() {
        canContinue = false;

        resetTimer();
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getTimer() {
        return timer;
    }

    public void tickTimer() {
        timer++;
    }

    private void resetTimer() {
        timer = 0;
    }

    private void setPrevious(BlockPos previous) {
        this.previous = previous;
    }
}
