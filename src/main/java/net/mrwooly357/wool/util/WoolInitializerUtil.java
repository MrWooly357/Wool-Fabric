package net.mrwooly357.wool.util;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.WoolInitializer;
import net.mrwooly357.wool.config.custom.WoolConfig;

import java.util.List;

public class WoolInitializerUtil {


    public static void invokeEnvious() {
        List<EntrypointContainer<WoolInitializer>> containers = FabricLoader.getInstance().getEntrypointContainers("wool", WoolInitializer.class);

        for (EntrypointContainer<WoolInitializer> container : containers) {

            try {
                container.getEntrypoint().onWoolInitialize();
            } catch (Throwable throwable) {

                if (WoolConfig.developerMode) Wool.LOGGER.error("An error occurred while initializing wool entrypoint for {}: {}", container.getProvider().getMetadata().getId(), throwable.getMessage());
            }
        }
    }
}
