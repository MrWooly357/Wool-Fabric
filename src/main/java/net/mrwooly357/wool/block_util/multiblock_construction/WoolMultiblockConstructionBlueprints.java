package net.mrwooly357.wool.block_util.multiblock_construction;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.registry.WoolRegistries;

public final class WoolMultiblockConstructionBlueprints {

    public static final MultiblockConstructionBlueprint EMPTY = register("empty", MultiblockConstructionBlueprint.builder()
            .build()
    );


    private static MultiblockConstructionBlueprint register(String name, MultiblockConstructionBlueprint blueprint) {
        return Registry.register(WoolRegistries.MULTIBLOCK_CONSTRUCTION_BLUEPRINT, Identifier.of(Wool.MOD_ID, name), blueprint);
    }

    public static void initialize() {
        Wool.logInitializing("multiblock construction blueprints");
    }
}
