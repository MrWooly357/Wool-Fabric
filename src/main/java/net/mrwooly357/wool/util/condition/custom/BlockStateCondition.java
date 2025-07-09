package net.mrwooly357.wool.util.condition.custom;

import net.minecraft.block.BlockState;
import net.mrwooly357.wool.util.condition.Condition;

@FunctionalInterface
public interface BlockStateCondition extends Condition<BlockState> {


    @Override
    boolean test(BlockState state);
}
