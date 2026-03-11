package net.mrwooly357.wool.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.registry.DynamicRegistryManager;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.wool.WoolConfig;

public final class WoolEvents {

    private WoolEvents() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate WoolEvents!");
    }


    public static void initialize() {
        Wool.logInitialization("events");
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            DynamicRegistryManager registryManager = server.getRegistryManager();

            if (WoolConfig.serverWorldDoesNotExist())
                WoolConfig.saveServerWorld(registryManager);

            WoolConfig.reloadServerWorld(registryManager);
        });
    }
}
