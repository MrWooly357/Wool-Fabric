package net.mrwooly357.wool.block.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a block that can have a {@link MultiblockConstructionBuilder}.
 */
public interface MultiblockConstructionBuilderBlock {


    /**
     * Logic for a situation when an item stack is used on this block.
     * @param usedItemStack the item stack used.
     */
    default void onUseWithItem(ItemStack usedItemStack) {
        MultiblockConstructionBuilder builder = createBuilder(usedItemStack);

        if (builder != null) {
            builder.start();
        }
    }

    /**
     * Ticks the {@link MultiblockConstructionBuilder} for this block. Call it in your block entity tick method.
     * @param start the start {@link BlockPos}.
     * @param end the end {@link BlockPos}.
     * @param usedItemStack the {@link ItemStack} used.
     */
    default void tickBuilder(BlockPos start, BlockPos end, ItemStack usedItemStack) {
        MultiblockConstructionBuilder builder = createBuilder(usedItemStack);

        if (builder != null) {
            builder.tickTimer();

            if (builder.getTimer() == builder.getDelay()) {
                builder.tryBuild(start, end);


                isBuilt(builder);
            }
        }
    }

    /**
     * Shows whether a multiblock construction has been built.
     * @param builder the {@link MultiblockConstructionBuilder} to check.
     * @return has multiblock construction been built.
     */
    default boolean isBuilt(MultiblockConstructionBuilder builder) {
        return builder.isSuccessful;
    }

    /**
     * Creates a new {@link MultiblockConstructionBuilder} for this block.
     * @param usedItemStack the {@link ItemStack} used.
     * @return a new {@link MultiblockConstructionBuilder} for this block.
     */
    default @Nullable MultiblockConstructionBuilder createBuilder(ItemStack usedItemStack) {
        if (usedItemStack.getItem() instanceof MultiblockConstructionBlueprintHolder holder && usedItemStack.getHolder() != null) {
            MultiblockConstructionBuilder builder = new MultiblockConstructionBuilder(holder.getBlueprint(), usedItemStack.getHolder().getWorld());

            builder.setDelay(getDelay());

            return builder;
        } else {
            return getDefaultBuilder();
        }
    }

    /**
     * Represents the default {@link MultiblockConstructionBuilder} for this block.
     * @return the default {@link MultiblockConstructionBuilder} for this block.
     */
    default @Nullable MultiblockConstructionBuilder getDefaultBuilder() {
        return null;
    }

    /**
     * Sets the delay for a new {@link MultiblockConstructionBuilder} in {@link MultiblockConstructionBuilderBlock#createBuilder(ItemStack)}.
     * @return the delay for a new {@link MultiblockConstructionBuilder}.
     */
    int getDelay();
}
