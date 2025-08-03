package net.mrwooly357.wool.block_util;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrwooly357.wool.block_util.entity.ExtendedBlockEntity;
import net.mrwooly357.wool.block_util.entity.TickableBlockEntity;
import org.jetbrains.annotations.Nullable;

public abstract class ExtendedBlockWithEntity extends BlockWithEntity {

    protected ExtendedBlockWithEntity(Settings settings) {
        super(settings);
    }


    protected abstract ExtendedBlockEntity createExtendedBlockEntity(BlockPos pos, BlockState state);

    @Override
    public final @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return createExtendedBlockEntity(pos, state);
    }

    @Override
    public final @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> {
            if (!world1.isClient()) {
                TickableBlockEntity.Server serverTickable = (TickableBlockEntity.Server) blockEntity;
                ServerWorld serverWorld = (ServerWorld) world1;

                if (serverTickable.canTickServer(serverWorld, pos, state1))
                    serverTickable.serverTick(serverWorld, pos, state1);
            } else {
                TickableBlockEntity.Client clientTickable = (TickableBlockEntity.Client) blockEntity;
                ClientWorld clientWorld = (ClientWorld) world1;

                if (clientTickable.canTickClient(clientWorld, pos, state1))
                    clientTickable.clientTick(clientWorld, pos, state1);
            }
        };
    }
}
