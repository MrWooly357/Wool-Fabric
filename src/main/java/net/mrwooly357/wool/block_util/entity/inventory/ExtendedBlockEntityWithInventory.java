package net.mrwooly357.wool.block_util.entity.inventory;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.wool.block_util.entity.ExtendedBlockEntity;

public abstract class ExtendedBlockEntityWithInventory extends ExtendedBlockEntity implements ImplementedInventory {

    protected final DefaultedList<ItemStack> inventory;

    protected ExtendedBlockEntityWithInventory(BlockEntityType<? extends ExtendedBlockEntityWithInventory> type, BlockPos pos, BlockState state, int inventorySize) {
        this(type, pos, state, inventorySize, ItemStack.EMPTY);
    }

    protected ExtendedBlockEntityWithInventory(BlockEntityType<? extends ExtendedBlockEntityWithInventory> type, BlockPos pos, BlockState state, int inventorySize, ItemStack defaultStack) {
        super(type, pos, state);

        inventory = DefaultedList.ofSize(inventorySize, defaultStack);

        addData((nbt, lookup) -> Inventories.writeNbt(nbt, inventory, lookup), (nbt, lookup) -> Inventories.readNbt(nbt, inventory, lookup));
    }


    @Override
    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack stack = Inventories.splitStack(getInventory(), slot, amount);

        if (!stack.isEmpty())
            markDirty();

        return stack;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        markDirty();
        ImplementedInventory.super.setStack(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }
}
