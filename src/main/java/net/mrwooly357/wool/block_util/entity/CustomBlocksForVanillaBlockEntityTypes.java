package net.mrwooly357.wool.block_util.entity;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HangingSignBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WoolConfig;

import java.util.*;

public class CustomBlocksForVanillaBlockEntityTypes {

    private static final Map<BlockEntityType.BlockEntityFactory<?>, List<List<? extends Block>>> FACTORIES = new HashMap<>();
    private static final List<SignBlock> CUSTOM_SIGNS = new ArrayList<>();
    private static final List<WallSignBlock> CUSTOM_WALL_SIGNS = new ArrayList<>();
    private static final List<HangingSignBlock> CUSTOM_HANGING_SIGNS = new ArrayList<>();
    private static final List<WallHangingSignBlock> CUSTOM_WALL_HANGING_SIGNS = new ArrayList<>();


    @SafeVarargs
    public static void addFactory(BlockEntityType.BlockEntityFactory<?> factory, List<? extends Block>... blocks) {
        FACTORIES.put(factory, List.of(blocks));
    }

    public static List<List<? extends Block>> getBlocksForFactory(BlockEntityType.BlockEntityFactory<?> factory) {
        return FACTORIES.get(factory);
    }

    public static boolean isFactoryRegistered(BlockEntityType.BlockEntityFactory<?> factory) {
        return FACTORIES.containsKey(factory);
    }

    public static void addSign(SignBlock sign) {
        CUSTOM_SIGNS.add(sign);
    }

    public static void addWallSign(WallSignBlock wallSign) {
        CUSTOM_WALL_SIGNS.add(wallSign);
    }

    public static void addHangingSign(HangingSignBlock hangingSign) {
        CUSTOM_HANGING_SIGNS.add(hangingSign);
    }

    public static void addWallHangingSign(WallHangingSignBlock wallHangingSign) {
        CUSTOM_WALL_HANGING_SIGNS.add(wallHangingSign);
    }

    public static void initialize() {
        if (WoolConfig.enableDeveloperMode)
            Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " factories for custom blocks for vanilla block entity types");

        addFactory(SignBlockEntity::new, CUSTOM_SIGNS, CUSTOM_WALL_SIGNS);
        addFactory(HangingSignBlockEntity::new, CUSTOM_HANGING_SIGNS, CUSTOM_WALL_HANGING_SIGNS);
    }
}
