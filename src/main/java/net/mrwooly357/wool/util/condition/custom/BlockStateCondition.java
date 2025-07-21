package net.mrwooly357.wool.util.condition.custom;

import net.minecraft.block.BlockState;
import net.mrwooly357.wool.util.condition.Condition;

/**
 * A {@link Condition condition} for {@link BlockState state}.
 * @since 1.0.0
 */
@FunctionalInterface
public interface BlockStateCondition extends Condition<BlockState> {


    /**
     * Tests the {@link BlockState state}.
     * @param state the {@link BlockState}.
     * @return {@code true} if requirement is met, otherwise {@code false}.
     */
    @Override
    boolean test(BlockState state);
}
