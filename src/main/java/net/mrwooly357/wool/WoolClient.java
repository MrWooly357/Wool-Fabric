package net.mrwooly357.wool;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.mrwooly357.wool.screen.WoolScreenHandlerTypes;
import net.mrwooly357.wool.screen.custom.accessory.PlayerAccessoryInventoryScreen;

public class WoolClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        HandledScreens.register(WoolScreenHandlerTypes.PLAYER_ACCESSORY_INVENTORY, PlayerAccessoryInventoryScreen::new);
    }
}
