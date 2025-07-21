package net.mrwooly357.wool.util.condition.custom;

import net.minecraft.util.math.BlockPos;
import net.mrwooly357.wool.util.condition.Condition;

/**
 * A {@link Condition condition} for {@link BlockPos pos}.
 * @since 1.0.0
 */
@FunctionalInterface
public interface BlockPosCondition extends Condition<BlockPos> {


    /**
     * Tests the {@link BlockPos pos}.
     * @param pos the {@link BlockPos}.
     * @return {@code true} if requirement is met, otherwise {@code false}.
     */
    @Override
    boolean test(BlockPos pos);
}
