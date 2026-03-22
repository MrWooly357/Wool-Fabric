package net.mrwooly357.wool;

import net.fabricmc.api.ClientModInitializer;
import net.mrwooly357.wool.command.WoolCommands;
import net.mrwooly357.wool.event.WoolEvents;

public final class WoolClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        WoolEvents.initializeClient();
        WoolCommands.initializeClient();
    }
}
