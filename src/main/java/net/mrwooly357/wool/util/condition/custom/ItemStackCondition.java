package net.mrwooly357.wool.util.condition.custom;

import net.minecraft.item.ItemStack;
import net.mrwooly357.wool.util.condition.Condition;

@FunctionalInterface
public interface ItemStackCondition extends Condition<ItemStack> {


    @Override
    boolean test(ItemStack stack);
}
