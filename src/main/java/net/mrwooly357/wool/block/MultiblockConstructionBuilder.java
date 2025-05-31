package net.mrwooly357.wool.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MultiblockConstructionBuilder {

    MultiblockConstructionBlueprint blueprint;
    World world;
    boolean canContinue;

    public MultiblockConstructionBuilder(MultiblockConstructionBlueprint blueprint, World world) {
        this.blueprint = blueprint;
        this.world = world;
    }


    public boolean tryBuild(BlockPos start, BlockPos end) {
        start();

        return buildSuccessful(start, end);
    }

    public void start() {
        canContinue = true;
    }

    public void stop() {
        canContinue = false;
    }

    public boolean buildSuccessful(BlockPos start, BlockPos end) {
        if (canContinue) {

            for (int a = 0; a < blueprint.getSizeInLayers(); a++) {
                MultiblockConstructionBlueprint.Layer layer = blueprint.getLayer(a);

                for (int b = 0; b < layer.getSizeInPatterns(); b++) {
                    List<@Nullable BlockState> pattern = layer.getDefinedPattern(b);

                    for (BlockState blockState : pattern) {
                        int x = start.getX();
                        int y = start.getY() + a;
                        int z = start.getZ();

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

                        BlockPos blockPosToCheck = new BlockPos(x, y, z);
                        BlockState blockStateToCheck = world.getBlockState(blockPosToCheck);

                        if (blockStateToCheck != blockState) {
                            stop();
                        } else if (blockPosToCheck == end) {
                            stop();

                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
