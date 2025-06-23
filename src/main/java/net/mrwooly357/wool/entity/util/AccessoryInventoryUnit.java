package net.mrwooly357.wool.entity.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import net.mrwooly357.wool.screen.slot.custom.accessory.AccessorySlotType;

public class AccessoryInventoryUnit {

    private final AccessorySlotType type;
    private ItemStack stack;

    public AccessoryInventoryUnit(AccessorySlotType type) {
        this(type, ItemStack.EMPTY);
    }

    public AccessoryInventoryUnit(AccessorySlotType type, ItemStack defaultStack) {
        this.type = type;
        this.stack = defaultStack;
    }


    private RegistryWrapper.WrapperLookup getLookup(World world) {
        return world.getRegistryManager();
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
}
