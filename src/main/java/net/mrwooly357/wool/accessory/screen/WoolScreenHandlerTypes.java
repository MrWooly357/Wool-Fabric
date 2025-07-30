package net.mrwooly357.wool.accessory.screen;

import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.accessory.screen.custom.PlayerAccessoryInventoryScreenHandler;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.helper.ScreenHandlerTypeRegistryHelper;

public class WoolScreenHandlerTypes {

    public static final ScreenHandlerType<PlayerAccessoryInventoryScreenHandler> PLAYER_ACCESSORY_INVENTORY = register(
            "player_accessory_inventory", new ScreenHandlerType<>(
                    (syncId, playerInventory) -> new PlayerAccessoryInventoryScreenHandler(syncId, playerInventory.player), FeatureFlags.VANILLA_FEATURES
            )
    );


    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String name, ScreenHandlerType<T> type) {
        return ScreenHandlerTypeRegistryHelper.register(Identifier.of(Wool.MOD_ID, name), type);
    }

    public static void initialize() {
        if (WoolConfig.enableDeveloperMode)
            Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " screen handler types");
    }
}
