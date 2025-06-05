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
        System.out.println("startPos: " + startPos);
        System.out.println("endPos: " + endPos);

        if (timer == delay) {

            resetTimer();
            setSuccessful(false);

            for (int a = 0; a < blueprint.getSizeInLayers(); a++) {
                MultiblockConstructionBlueprint.Layer layer = blueprint.getLayer(a);

                for (int b = 0; b < layer.getSizeInPatterns(); b++) {
                    List<List<@Nullable BlockState>> pattern = layer.getDefinedPattern(b);
                    boolean first = true;
                    boolean toNextPattern = b > 0 && previousPos.getX() == firstPatternPos.getX() && previousPos.getY() == firstPatternPos.getY() && previousPos.getZ() == firstPatternPos.getZ();
                    System.out.println("toNextPattern: " + toNextPattern);

                    for (List<@Nullable BlockState> states : pattern) {
                        boolean shouldReturn = false;

                        if (previousPos == null) setPreviousPos(startPos);

                        System.out.println("a: " + a);
                        BlockPos posToCheck = getPosToCheck(a, direction, first, toNextPattern);
                        toNextPattern = false;
                        System.out.println("posToCheck: " + posToCheck);
                        BlockState stateToCheck = world.getBlockState(posToCheck);

                        if (first) {
                            setFirstPatternPos(posToCheck);
                            setLastPatternPos(getLastPatternPos(firstPatternPos, pattern, direction));

                            first = false;

                            System.out.println("firstPatternPos: " + firstPatternPos);
                            System.out.println("lastPatternPos: " + lastPatternPos);
                        }

                        for (@Nullable BlockState state : states) {

                            if (stateToCheck != state && state != null && state == states.getLast()) {
                                return;
                            } else if (stateToCheck == state || state == null) {

                                if (posToCheck.getX() == lastPatternPos.getX() && posToCheck.getY() == lastPatternPos.getY() && posToCheck.getZ() == lastPatternPos.getZ()) {

                                    if (posToCheck.getX() == endPos.getX() && posToCheck.getZ() == endPos.getZ()) {
                                        setPreviousPos(new BlockPos(startPos.getX(), firstPatternPos.getY(), startPos.getZ()));
                                    } else {
                                        setPreviousPos(firstPatternPos);
                                    }

                                    first = true;
                                    shouldReturn = true;

                                    System.out.println("previousPos: " + previousPos);
                                    System.out.println("posToCheck: " + posToCheck);
                                    System.out.println("firstPatternPos: " + firstPatternPos);
                                    System.out.println("success");
                                    System.out.println("-----");
                                } else {
                                    setPreviousPos(posToCheck);

                                    System.out.println("previousPos: " + previousPos);
                                    System.out.println("posToCheck: " + posToCheck);
                                    System.out.println("fail");
                                    System.out.println("-----");
                                }

                                break;
                            }
                        }

                        if (posToCheck.getX() == endPos.getX() && posToCheck.getY() == endPos.getY() && posToCheck.getZ() == endPos.getZ() && shouldReturn) {
                            setSuccessful(true);
                            System.out.println(isSuccessful);

                            return;
                        }
                    }
                }
            }
        }
    }

    private @NotNull BlockPos getPosToCheck(int a, Direction direction, boolean first, boolean toNextPattern) {
        int x = previousPos.getX();
        int y = previousPos.getY() + a;
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

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
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
