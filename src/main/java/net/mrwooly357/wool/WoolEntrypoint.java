package net.mrwooly357.wool;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.mrwooly357.wool.config.custom.WoolConfig;

import java.util.List;

/**
 * This interface is used for mod initializer classes of mods that use Wool. You should implement it in your mod initializer class.
 */
public interface WoolEntrypoint {


    /**
     * Called when Wool is initialized.
     */
    void onWoolInitialized();

    static void invokeEntrypoint() {
        List<EntrypointContainer<WoolEntrypoint>> containers = FabricLoader.getInstance().getEntrypointContainers("wool", WoolEntrypoint.class);

        for (EntrypointContainer<WoolEntrypoint> container : containers) {

            try {
                container.getEntrypoint().onWoolInitialized();
            } catch (Throwable throwable) {

                if (WoolConfig.developerMode)
                    Wool.LOGGER.error("An error occurred while initializing wool entrypoint for {}: {}", container.getProvider().getMetadata().getId(), throwable.getMessage());
            }
        }
    }
}
