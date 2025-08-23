package net.mrwooly357.wool.block_util.entity.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.mrwooly357.wool.util.random.RandomUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;
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

    default boolean isNotEmpty() {
        return !isEmpty();
    }

    default boolean isFull() {
        for (ItemStack stack : getInventory()) {

            if (stack.isEmpty())
                return false;
        }

        return true;
    }

    default boolean isNotFull() {
        return !isFull();
    }

    @Override
    default ItemStack getStack(int slot) {
        return getInventory().get(slot);
    }

    @Override
    default ItemStack removeStack(int slot) {
        return Inventories.removeStack(getInventory(), slot);
    }

    @Override
    default ItemStack removeStack(int slot, int amount) {
        ItemStack stack = Inventories.splitStack(getInventory(), slot, amount);

        if (!stack.isEmpty())
            markDirty();

        return stack;
    }

    default ItemStack removeFirstStack(Predicate<ItemStack> predicate) {
        for (int i = 0; i < size(); i++) {

            if (predicate.test(getStack(i)))
                return removeStack(i);
        }

        return ItemStack.EMPTY;
    }

    default void removeFirstNonEmptyStack() {
        removeFirstStack(stack -> !stack.isEmpty());
    }

    default void removeRandomStack(Predicate<ItemStack> predicate) {
        for (int i = 0; i < size(); i++) {

            if (predicate.test(getStack(i)))
                break;
        }

        while (true) {
            int slot = RandomUtil.nextInt(0, size() - 1);

            if (predicate.test(getStack(slot))) {
                removeStack(slot);

                break;
            }
        }
    }

    @Override
    default void setStack(int slot, ItemStack stack) {
        getInventory().set(slot, stack);

        if (stack.getCount() > getMaxCountPerStack())
            stack.setCount(getMaxCountPerStack());
    }

    default void setStackInFirstEmpty(ItemStack stack) {
        for (int i = 0; i < size(); i++) {

            if (getStack(i).isEmpty()) {
                setStack(i, stack);

                break;
            }
        }
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

    @Nullable
    default ItemStack findFirstStack(Predicate<ItemStack> predicate) {
        return getInventory().stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    @Nullable
    default ItemStack findFirstNonEmptyStack() {
        return findFirstStack(stack -> !stack.isEmpty());
    }

    @Nullable
    default ItemStack selectRandomStack(Predicate<ItemStack> predicate) {
        List<ItemStack> candidates = getInventory().stream()
                .filter(predicate)
                .toList();

        if (!candidates.isEmpty())
            return RandomUtil.select(candidates);

        return null;
    }

    @Nullable
    default ItemStack selectRandomNonEmptyStack() {
        return selectRandomStack(stack -> !stack.isEmpty());
    }
}
