package net.mrwooly357.wool;

import net.fabricmc.api.ClientModInitializer;
import net.mrwooly357.wool.config.custom.wool.WoolConfig;

public final class WoolClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        if (WoolConfig.clientDoesNotExist())
            WoolConfig.saveClient();

        WoolConfig.reloadClient();
    }
}
