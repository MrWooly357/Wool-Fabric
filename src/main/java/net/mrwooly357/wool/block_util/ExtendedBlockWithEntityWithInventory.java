package net.mrwooly357.wool.block_util;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.wool.block_util.entity.ExtendedBlockEntity;
import net.mrwooly357.wool.block_util.entity.inventory.ExtendedBlockEntityWithInventory;

public abstract class ExtendedBlockWithEntityWithInventory extends ExtendedBlockWithEntity {

    protected ExtendedBlockWithEntityWithInventory(Settings settings) {
        super(settings);
    }


    protected abstract ExtendedBlockEntityWithInventory createExtendedBlockEntityWithInventory(BlockPos pos, BlockState state);

    @Override
    protected final ExtendedBlockEntity createExtendedBlockEntity(BlockPos pos, BlockState state) {
        return createExtendedBlockEntityWithInventory(pos, state);
    }
}
