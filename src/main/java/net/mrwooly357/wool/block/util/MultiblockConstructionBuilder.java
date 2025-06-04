package net.mrwooly357.wool.block.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MultiblockConstructionBuilder {

    private final MultiblockConstructionBlueprint blueprint;
    private final World world;
    private int delay;
    private int timer;
    private BlockPos previousPos;
    private BlockPos firstPatternPos;
    private BlockPos lastPatternPos;
    public boolean isSuccessful;

    public MultiblockConstructionBuilder(MultiblockConstructionBlueprint blueprint, World world) {
        this.blueprint = blueprint;
        this.world = world;
    }


    public void tryBuild(BlockPos startPos, BlockPos endPos, Direction direction) {
        resetTimer();
        System.out.println(3);

        for (int a = 0; a < blueprint.getSizeInLayers(); a++) {
            MultiblockConstructionBlueprint.Layer layer = blueprint.getLayer(a);
            System.out.println("Layer: " + layer);

            for (int b = 0; b < layer.getSizeInPatterns(); b++) {
                List<List<@Nullable BlockState>> pattern = layer.getDefinedPattern(b);
                boolean first = true;
                boolean toNextPattern = b > 0;
                System.out.println("Pattern: " + pattern);

                for (List<@Nullable BlockState> states : pattern) {
                    boolean shouldBreak = false;
                    System.out.println(pattern.getLast());
                    System.out.println("States: " + states);

                    if (previousPos == null) {
                        System.out.println(4);
                        setPreviousPos(startPos);
                    }

                    BlockPos posToCheck = getPosToCheck(a, direction, first, toNextPattern);

                    if (first) {
                        setFirstPatternPos(posToCheck);
                        setLastPatternPos(getLastPatternPos(firstPatternPos, pattern, direction));
                        System.out.println("First pattern pos: " + firstPatternPos);
                        System.out.println("Last pattern pos: " + lastPatternPos);
                        System.out.println("Previous pos: " + previousPos);

                        first = false;
                    }

                    System.out.println(posToCheck);
                    BlockState stateToCheck = world.getBlockState(posToCheck);
                    System.out.println(stateToCheck);

                    for (@Nullable BlockState state : states) {
                        System.out.println(state);

                        if (stateToCheck != state && state != null && state == states.getLast()) {
                            System.out.println(8);

                            return;
                        } else if (stateToCheck == state || state == null) {
                            System.out.println("posToCheck: " + posToCheck);
                            System.out.println("lastPatternPos: " + lastPatternPos);

                            if (posToCheck.getX() == lastPatternPos.getX() && posToCheck.getY() == lastPatternPos.getY() && posToCheck.getZ() == lastPatternPos.getZ()) {
                                System.out.println("success");
                                setPreviousPos(firstPatternPos);

                                first = true;
                                shouldBreak = true;
                            } else {
                                System.out.println("fail");
                                setPreviousPos(posToCheck);
                            }

                            System.out.println(9);

                            break;
                        }
                    }

                    if (posToCheck == endPos && shouldBreak) {
                        isSuccessful = true;

                        System.out.println(10);

                        return;
                    }
                }
            }
        }
    }

    private @NotNull BlockPos getPosToCheck(int a, Direction direction, boolean first, boolean toNextPattern) {
        int x = previousPos.getX();
        int y = previousPos.getY() + a;
        int z = previousPos.getZ();

        if (toNextPattern && previousPos == firstPatternPos) {

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

    public void setFirstPatternPos(BlockPos firstPatternPos) {
        this.firstPatternPos = firstPatternPos;
    }

    public void setLastPatternPos(BlockPos lastPatternPos) {
        this.lastPatternPos = lastPatternPos;
    }

    public BlockPos getLastPatternPos(BlockPos firstPatternPos, List<List<@Nullable BlockState>> pattern, Direction direction) {
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
