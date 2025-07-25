package net.mrwooly357.wool.registry.helper;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.config.Config;
import net.mrwooly357.wool.config.ConfigManager;
import net.mrwooly357.wool.registry.WoolRegistries;

/**
 * A helper used for registering custom {@link Config}s
 */
public interface ConfigRegistryHelper {


    /**
     * Registers a custom {@link Config}.
     * @param id the {@link Identifier}.
     * @param config the {@link Config}.
     * @return a registered {@link Config}.
     */
    static Config register(Identifier id, Config config) {
        config.load();
        ConfigManager.getIdsToConfigs().put(id, config);

        return Registry.register(WoolRegistries.CONFIG, id, config);
    }
}
