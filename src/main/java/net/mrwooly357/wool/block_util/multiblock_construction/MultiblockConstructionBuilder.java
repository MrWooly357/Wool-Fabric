package net.mrwooly357.wool.block_util.multiblock_construction;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class MultiblockConstructionBuilder {

    private final MultiblockConstructionBlueprint blueprint;
    private final World world;
    private BlockPos previousPos;
    private BlockPos firstPatternPos;
    private BlockPos lastPatternPos;

    public MultiblockConstructionBuilder(MultiblockConstructionBlueprint blueprint, World world) {
        this.blueprint = blueprint;
        this.world = world;
    }


    public boolean tryBuild(BlockPos startPos, BlockPos endPos, Direction direction) {
        for (int a = 0; a < blueprint.getSizeInLayers(); a++) {
            MultiblockConstructionBlueprint.Layer layer = blueprint.getLayer(a);

            for (int b = 0; b < layer.getSizeInPatterns(); b++) {
                List<List<@Nullable BlockState>> pattern = layer.getDefinedPattern(b);
                boolean first = true;
                boolean toNextPattern = b > 0 && previousPos.getX() == firstPatternPos.getX() && previousPos.getY() == firstPatternPos.getY() && previousPos.getZ() == firstPatternPos.getZ();

                for (List<@Nullable BlockState> states : pattern) {
                    boolean shouldReturn = false;

                    if (previousPos == null)
                        previousPos = startPos;

                    BlockPos posToCheck = calculatePosToCheck(startPos, a, direction, first, toNextPattern);
                    toNextPattern = false;
                    BlockState stateToCheck = world.getBlockState(posToCheck);

                    if (first) {
                        firstPatternPos = posToCheck;
                        lastPatternPos = calculateLastPatternPos(firstPatternPos, pattern, direction);
                        first = false;
                    }

                    for (@Nullable BlockState state : states) {

                        if (stateToCheck != state && state != null && state == states.getLast()) {
                            return false;
                        } else if (stateToCheck == state || state == null) {

                            if (posToCheck.getX() == lastPatternPos.getX() && posToCheck.getY() == lastPatternPos.getY() && posToCheck.getZ() == lastPatternPos.getZ()) {

                                if (posToCheck.getX() == endPos.getX() && posToCheck.getZ() == endPos.getZ()) {
                                    previousPos = new BlockPos(startPos.getX(), firstPatternPos.getY(), startPos.getZ());
                                } else
                                    previousPos = firstPatternPos;

                                first = true;
                                shouldReturn = true;
                            } else
                                previousPos = posToCheck;

                            break;
                        }
                    }

                    if (posToCheck.getX() == endPos.getX() && posToCheck.getY() == endPos.getY() && posToCheck.getZ() == endPos.getZ() && shouldReturn)
                        return true;
                }
            }
        }

        return false;
    }

    private @NotNull BlockPos calculatePosToCheck(BlockPos startPos, int a, Direction direction, boolean first, boolean toNextPattern) {
        int x = previousPos.getX();
        int y = startPos.getY() + a;
        int z = previousPos.getZ();

        if (toNextPattern) {

            if (direction == Direction.NORTH) {
                z--;
            } else if (direction == Direction.EAST) {
                x++;
            } else if (direction == Direction.SOUTH) {
                z++;
            } else if (direction == Direction.WEST) {
                x--;
            }
        }

        if (!first) {

            if (direction == Direction.NORTH) {
                x--;
            } else if (direction == Direction.EAST) {
                z--;
            } else if (direction == Direction.SOUTH) {
                x++;
            } else if (direction == Direction.WEST) {
                z++;
            }
        }

        return new BlockPos(x, y, z);
    }

    private static BlockPos calculateLastPatternPos(BlockPos firstPatternPos, List<List<@Nullable BlockState>> pattern, Direction direction) {
        int x = firstPatternPos.getX();
        int y = firstPatternPos.getY();
        int z = firstPatternPos.getZ();

        if (direction == Direction.NORTH) {
            x -= pattern.size();
            x++;
        } else if (direction == Direction.EAST) {
            z -= pattern.size();
            z++;
        } else if (direction == Direction.SOUTH) {
            x += pattern.size();
            x--;
        } else if (direction == Direction.WEST) {
            z += pattern.size();
            z--;
        }

        return new BlockPos(x, y, z);
    }
}
