package net.mrwooly357.wool.util.condition.custom;

import net.minecraft.item.ItemStack;
import net.mrwooly357.wool.util.condition.Condition;

/**
 * A {@link Condition condition} for {@link ItemStack stack}.
 * @since 1.0.0
 */
@FunctionalInterface
public interface ItemStackCondition extends Condition<ItemStack> {


    /**
     * Tests the {@link ItemStack stack}.
     * @param stack the {@link ItemStack}.
     * @return {@code true} if requirement is met, otherwise {@code false}.
     */
    @Override
    boolean test(ItemStack stack);
}
