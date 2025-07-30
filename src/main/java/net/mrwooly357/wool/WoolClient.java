package net.mrwooly357.wool;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.resource.ResourceType;
import net.mrwooly357.wool.animation.Animation;
import net.mrwooly357.wool.accessory.screen.WoolScreenHandlerTypes;
import net.mrwooly357.wool.accessory.screen.custom.PlayerAccessoryInventoryScreen;

public class WoolClient implements ClientModInitializer {

    public static final Animation.Loader ANIMATION_LOADER = new Animation.Loader();


    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(ANIMATION_LOADER);
        HandledScreens.register(WoolScreenHandlerTypes.PLAYER_ACCESSORY_INVENTORY, PlayerAccessoryInventoryScreen::new);
    }
}
