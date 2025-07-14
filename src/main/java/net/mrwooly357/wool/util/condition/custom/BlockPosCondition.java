package net.mrwooly357.wool.util.condition.custom;

import net.minecraft.util.math.BlockPos;
import net.mrwooly357.wool.util.condition.Condition;

@FunctionalInterface
public interface BlockPosCondition extends Condition<BlockPos> {


    @Override
    boolean test(BlockPos pos);
}
