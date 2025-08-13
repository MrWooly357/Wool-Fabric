package net.mrwooly357.wool.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.mrwooly357.wool.block_util.entity.CustomBlocksForVanillaBlockEntities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mixin(BlockEntityType.Builder.class)
public abstract class CustomBlockToVanillaBlockEntityTypeAdderMixin {


    @Invoker("<init>")
    private static <BE extends BlockEntity> BlockEntityType.Builder<BE> invokeConstructor(BlockEntityType.BlockEntityFactory<? extends BE> factory, Set<Block> blocks) {
        throw new AssertionError();
    }

    @Inject(method = "create", at = @At("RETURN"), cancellable = true)
    private static <BE extends BlockEntity> void injectCreate(BlockEntityType.BlockEntityFactory<? extends BE> factory, Block[] blocks, CallbackInfoReturnable<BlockEntityType.Builder<BE>> cir) {
        Set<Block> allBlocks = new HashSet<>();
        Set<Block> vanillaBlocks = new HashSet<>(List.of(blocks));
        Set<Block> customBlocks = new HashSet<>();

        CustomBlocksForVanillaBlockEntities.forEachAdder(adder -> adder.addCustomBlocks(vanillaBlocks, customBlocks));

        allBlocks.addAll(vanillaBlocks);
        allBlocks.addAll(customBlocks);
        cir.setReturnValue(invokeConstructor(factory, allBlocks));
    }
}
