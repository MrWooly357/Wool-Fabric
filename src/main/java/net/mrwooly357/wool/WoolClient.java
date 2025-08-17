package net.mrwooly357.wool;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.mrwooly357.wool.animation.Animation;
import net.mrwooly357.wool.accessory.screen.WoolScreenHandlerTypes;
import net.mrwooly357.wool.accessory.screen.custom.PlayerAccessoryInventoryScreen;

@Environment(EnvType.CLIENT)
public final class WoolClient implements ClientModInitializer {

    public static final Animation.Loader ANIMATION_LOADER = new Animation.Loader();


    @Override
    public void onInitializeClient() {
        // Handled screens
        HandledScreens.register(WoolScreenHandlerTypes.PLAYER_ACCESSORY_INVENTORY, PlayerAccessoryInventoryScreen::new);

        // In development
        //ResourceManagerHelper.getAsString(ResourceType.CLIENT_RESOURCES).registerReloadListener(ANIMATION_LOADER);
    }
}
