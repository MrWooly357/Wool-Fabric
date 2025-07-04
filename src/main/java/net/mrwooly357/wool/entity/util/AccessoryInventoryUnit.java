package net.mrwooly357.wool.entity.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.mrwooly357.wool.screen.slot.custom.accessory.AccessorySlotType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AccessoryInventoryUnit {

    private final AccessorySlotType type;
    private ItemStack stack;
    private boolean available;

    public AccessoryInventoryUnit(AccessorySlotType type, boolean availableByDefault) {
        this(type, ItemStack.EMPTY, availableByDefault);
    }

    public AccessoryInventoryUnit(AccessorySlotType type, ItemStack defaultStack, boolean availableByDefault) {
        this.type = type;
        this.stack = defaultStack;
        available = availableByDefault;
    }


    public AccessorySlotType getType() {
        return type;
    }

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public static NbtCompound toNbt(World world, AccessoryInventoryUnit unit) {
        ItemStack stack = unit.getStack();
        NbtCompound nbt = new NbtCompound();

        if (!stack.isEmpty()) {
            RegistryWrapper.WrapperLookup lookup = world.getRegistryManager();

            nbt.put("Stack", unit.getStack().encode(lookup));
        }

        return nbt;
    }

    public static ItemStack fromNbt(NbtCompound nbt, World world) {
        RegistryWrapper.WrapperLookup lookup = world.getRegistryManager();
        NbtCompound compound = nbt.getCompound("Stack");

        return ItemStack.fromNbt(lookup, compound).isPresent() ? ItemStack.fromNbt(lookup, compound).get() : ItemStack.EMPTY;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Nullable
    public Identifier getIcon() {
        return stack.isEmpty() ? type.getIcon() : null;
    }


    public static class Wrapper implements Inventory {

        private final List<AccessoryInventoryUnit> units;

        public Wrapper(List<AccessoryInventoryUnit> units) {
            this.units = units;
        }


        @Override
        public int size() {
            return units.size();
        }

        @Override
        public boolean isEmpty() {
            for (AccessoryInventoryUnit unit : units) {

                if (!unit.getStack().isEmpty())
                    return false;
            }

            return true;
        }

        @Override
        public ItemStack getStack(int slot) {
            return units.get(slot).getStack();
        }

        @Override
        public ItemStack removeStack(int slot, int amount) {
            ItemStack stack = units.get(slot).getStack();
            Item item = stack.getItem();

            stack.decrement(amount);

            return new ItemStack(item, amount);
        }

        @Override
        public ItemStack removeStack(int slot) {
            AccessoryInventoryUnit unit = units.get(slot);
            ItemStack stack = unit.getStack();

            unit.setStack(ItemStack.EMPTY);

            return stack;
        }

        @Override
        public void setStack(int slot, ItemStack stack) {
            units.get(slot).setStack(stack);
        }

        @Override
        public void markDirty() {}

        @Override
        public boolean canPlayerUse(PlayerEntity player) {
            return true;
        }

        @Override
        public void clear() {
            for (AccessoryInventoryUnit unit : units)
                unit.setStack(ItemStack.EMPTY);
        }
    }
}
