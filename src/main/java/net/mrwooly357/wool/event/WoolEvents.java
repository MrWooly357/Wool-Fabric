package net.mrwooly357.wool.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.integrated.IntegratedServer;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.ConfigManagersRegistry;

public final class WoolEvents {

    private WoolEvents() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate WoolEvents!");
    }


    public static void initialize() {
        Wool.logInitialization("events");
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            ConfigManagersRegistry.forEachGeneralServer((id, manager) -> {
                if (manager.doesNotExist())
                    manager.save();

                manager.load();
            });
            String name = server.getSaveProperties().getLevelName();
            DynamicRegistryManager registryManager = server.getRegistryManager();
            ConfigManagersRegistry.forEachServerWorld((id, manager) -> {
                if (manager.doesNotExist(name))
                    manager.save(name, registryManager);

                manager.load(name, registryManager);
            });
        });
    }

    public static void initializeClient() {
        Wool.logInitialization("client events");
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> ConfigManagersRegistry.forEachGeneralClient((id, manager) -> {
            if (manager.doesNotExist())
                manager.save();

            manager.load();
        }));
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            String name;
            IntegratedServer server = client.getServer();

            if (server != null)
                name = server.getSaveProperties().getLevelName();
            else {
                ServerInfo info = client.getCurrentServerEntry();

                if (info != null)
                    name = info.name;
                else
                    name = "unknown";
            }

            DynamicRegistryManager registryManager = handler.getRegistryManager();
            ConfigManagersRegistry.forEachClientWorld((id, manager) -> {
                if (manager.doesNotExist(name))
                    manager.save(name, registryManager);

                manager.load(name, registryManager);
            });
        });
    }
}
