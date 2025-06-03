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
    private BlockPos previousPos;
    public boolean isSuccessful;

    public MultiblockConstructionBuilder(MultiblockConstructionBlueprint blueprint, World world) {
        this.blueprint = blueprint;
        this.world = world;
    }


    public void tryBuild(BlockPos startPos, BlockPos endPos) {
        if (canContinue) {
            resetTimer();

            for (int a = 0; a < blueprint.getSizeInLayers(); a++) {
                MultiblockConstructionBlueprint.Layer layer = blueprint.getLayer(a);

                for (int b = 0; b < layer.getSizeInPatterns(); b++) {
                    List<List<@Nullable BlockState>> pattern = layer.getDefinedPattern(b);

                    for (List<@Nullable BlockState> states : pattern) {

                        if (previousPos == null) {
                            setPreviousPos(startPos);
                        }

                        BlockPos posToCheck = getPosToCheck(endPos, b);
                        BlockState stateToCheck = world.getBlockState(posToCheck);

                        for (@Nullable BlockState state : states) {

                            if (stateToCheck != state && state != null) {
                                stop();
                            } else {
                                setPreviousPos(posToCheck);

                                break;
                            }
                        }

                        if (posToCheck == endPos) {
                            stop();

                            isSuccessful = true;

                            return;
                        }
                    }
                }
            }
        }
    }

    private @NotNull BlockPos getPosToCheck(BlockPos endPos, int b) {
        int x = previousPos.getX();
        int y = previousPos.getY() + b;
        int z = previousPos.getZ();

        if (x < endPos.getX()) {
            x++;
        } else if (x > endPos.getX()) {
            x--;
        }

        if (z < endPos.getZ()) {
            z++;
        } else if (z > endPos.getZ()) {
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

    private void setPreviousPos(BlockPos previousPos) {
        this.previousPos = previousPos;
    }
}
