package net.mrwooly357.wool.block.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
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
     * @param usedStack the {@link ItemStack} used.
     */
    default void tickBuilder(BlockPos start, BlockPos end, @Nullable ItemStack usedStack, @NotNull MultiblockConstructionBlueprint fallbackBlueprint, @NotNull World fallbackWorld) {
        MultiblockConstructionBuilder builder;

        if (usedStack != null) {
            builder = createBuilder(usedStack);
        } else {
            builder = new MultiblockConstructionBuilder(fallbackBlueprint, fallbackWorld);
        }


        if (builder != null) {
            builder.tickTimer();

            if (builder.getTimer() == builder.getDelay()) {
                builder.tryBuild(start, end);

                if (builder.isSuccessful) {
                    onBuilt(usedStack, builder, fallbackBlueprint, fallbackWorld);
                }
            }
        }
    }

    /**
     * Logic applied when multiblock construction is built.
     * @param usedStack the {@link ItemStack} used.
     * @param builder the {@link MultiblockConstructionBuilder} used.
     * @param fallbackBlueprint the {@link MultiblockConstructionBlueprint} fallback.
     * @param fallbackWorld the {@link World} fallback.
     */
    void onBuilt(@Nullable ItemStack usedStack, MultiblockConstructionBuilder builder, @NotNull MultiblockConstructionBlueprint fallbackBlueprint, @NotNull World fallbackWorld);

    /**
     * Creates a new {@link MultiblockConstructionBuilder} for this block.
     * @param usedStack the {@link ItemStack} used.
     * @return a new {@link MultiblockConstructionBuilder} for this block.
     */
    default @Nullable MultiblockConstructionBuilder createBuilder(ItemStack usedStack) {
        if (usedStack.getItem() instanceof MultiblockConstructionBlueprintHolder holder && usedStack.getHolder() != null) {
            MultiblockConstructionBuilder builder = new MultiblockConstructionBuilder(holder.getBlueprint(), usedStack.getHolder().getWorld());

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
