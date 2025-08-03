package net.mrwooly357.wool.block_util.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public interface TickableBlockEntity {


    interface Server {


        boolean canTickServer(ServerWorld serverWorld, BlockPos pos, BlockState state, BlockEntity blockEntity);

        void serverTick(ServerWorld serverWorld, BlockPos pos, BlockState state, BlockEntity blockEntity);
    }


    interface Client {


        boolean canTickClient(ClientWorld clientWorld, BlockPos pos, BlockState state, BlockEntity blockEntity);

        void clientTick(ClientWorld clientWorld, BlockPos pos, BlockState state, BlockEntity blockEntity);
    }
}
