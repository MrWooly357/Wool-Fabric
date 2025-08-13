package net.mrwooly357.wool.block_util.entity;

import net.minecraft.block.Block;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public final class CustomBlocksForVanillaBlockEntities {

    private static final Set<Adder> ADDERS = new HashSet<>();


    public static void addAdder(Adder adder) {
        ADDERS.add(adder);
    }

    public static void forEachAdder(Consumer<? super Adder> action) {
        ADDERS.forEach(action);
    }


    @FunctionalInterface
    public interface Adder {


        void addCustomBlocks(Set<Block> vanillaBlocks, Set<Block> blocks);
    }
}
