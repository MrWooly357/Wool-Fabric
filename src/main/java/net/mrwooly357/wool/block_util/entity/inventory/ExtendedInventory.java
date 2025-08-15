package net.mrwooly357.wool.block_util.entity.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.mrwooly357.wool.util.random.RandomUtil;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public interface ExtendedInventory extends SidedInventory {


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
        ItemStack stack = Inventories.splitStack(getInventory(), slot, amount);

        if (!stack.isEmpty())
            markDirty();

        return stack;
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
    default boolean canInsert(int slot, ItemStack stack, @Nullable Direction direction) {
        return true;
    }

    @Override
    default boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return true;
    }

    default ItemStack findFirstNonEmpty() {
        return findFirst(stack -> !stack.isEmpty());
    }

    default ItemStack findFirst(Predicate<ItemStack> predicate) {
        return getInventory().stream()
                .filter(predicate)
                .findFirst()
                .orElse(ItemStack.EMPTY);
    }

    default ItemStack selectRandomNonEmpty() {
        return selectRandom(stack -> !stack.isEmpty());
    }

    default ItemStack selectRandom(Predicate<ItemStack> predicate) {
        ItemStack stack = RandomUtil.select(getInventory());

        while (!predicate.test(stack))
            stack = RandomUtil.select(getInventory());

        return stack;
    }
}
