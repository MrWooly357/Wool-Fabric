package net.mrwooly357.wool.block.entity.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public interface ImplementedInventory extends SidedInventory {

    DefaultedList<ItemStack> getInventory();

    @Override
    default int size() {
        return getInventory().size();
    }

    @Override
    default boolean isEmpty() {
        for (ItemStack stack : getInventory()) {

            if (!stack.isEmpty())
                return false;
        }

        return true;
    }

    @Override
    default ItemStack getStack(int slot) {
        return getInventory().get(slot);
    }

    @Override
    default ItemStack removeStack(int slot, int amount) {
        ItemStack result = Inventories.splitStack(getInventory(), slot, amount);

        if (!result.isEmpty())
            markDirty();

        return result;
    }

    @Override
    default ItemStack removeStack(int slot) {
        return Inventories.removeStack(getInventory(), slot);
    }

    @Override
    default void setStack(int slot, ItemStack stack) {
        getInventory().set(slot, stack);

        if (stack.getCount() > getMaxCountPerStack())
            stack.setCount(getMaxCountPerStack());
    }

    @Override
    default boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    default void clear() {
        getInventory().clear();
    }

    @Override
    default int[] getAvailableSlots(Direction side) {
        int[] result = new int[size()];

        for (int i = 0; i < result.length; i++)
            result[i] = i;

        return result;
    }

    @Override
    default boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return true;
    }

    @Override
    default boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }
}
