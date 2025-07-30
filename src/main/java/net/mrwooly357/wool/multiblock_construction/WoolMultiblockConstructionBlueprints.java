package net.mrwooly357.wool.multiblock_construction;

import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.helper.MultiblockConstructionBlueprintRegistryHelper;

public final class WoolMultiblockConstructionBlueprints {

    public static final MultiblockConstructionBlueprint EMPTY = register(
            "empty", new MultiblockConstructionBlueprint()
    );


    private static MultiblockConstructionBlueprint register(String name, MultiblockConstructionBlueprint blueprint) {
        return MultiblockConstructionBlueprintRegistryHelper.register(Identifier.of(Wool.MOD_ID, name), blueprint);
    }

    public static void initialize() {
        if (WoolConfig.enableDeveloperMode)
            Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " multiblock construction blueprints");
    }
}
