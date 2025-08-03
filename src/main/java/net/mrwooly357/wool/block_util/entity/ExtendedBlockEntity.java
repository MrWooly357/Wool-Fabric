package net.mrwooly357.wool.block_util.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class ExtendedBlockEntity extends BlockEntity implements TickableBlockEntity.Server, TickableBlockEntity.Client {

    protected final List<Data> data;

    protected ExtendedBlockEntity(BlockEntityType<? extends ExtendedBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        data = new ArrayList<>();
    }


    protected void addData(BiConsumer<NbtCompound, RegistryWrapper.WrapperLookup> encoder, BiConsumer<NbtCompound, RegistryWrapper.WrapperLookup> decoder) {
        data.add(new Data(encoder, decoder));
    }


    @Override
    public boolean canTickServer(ServerWorld serverWorld, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public void serverTick(ServerWorld serverWorld, BlockPos pos, BlockState state) {}

    @Override
    public boolean canTickClient(ClientWorld clientWorld, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public void clientTick(ClientWorld clientWorld, BlockPos pos, BlockState state) {}

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        for (Data data : this.data)
            data.encoder().accept(nbt, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        for (Data data : this.data)
            data.decoder().accept(nbt, registryLookup);
    }
}
