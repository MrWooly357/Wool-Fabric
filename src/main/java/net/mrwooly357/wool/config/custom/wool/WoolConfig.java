package net.mrwooly357.wool.config.custom.wool;

import net.minecraft.registry.DynamicRegistryManager;
import net.mrwooly357.wool.config.Config;
import net.mrwooly357.wool.config.ConfigUniverse;

import java.util.Optional;

public final class WoolConfig {

    private static final ConfigUniverse<WoolServerConfig, WoolServerWorldConfig, WoolClientConfig, WoolClientWorldConfig> CONFIGS = new ConfigUniverse<>(
            Optional.of(WoolServerConfig.DEFAULT),
            access -> access.set(WoolServerConfig.load()),
            Optional.of(WoolServerWorldConfig.DEFAULT),
            (access, registryManager) -> access.set(WoolServerWorldConfig.load(registryManager)),
            Optional.of(WoolClientConfig.DEFAULT),
            access -> access.set(WoolClientConfig.load()),
            Optional.of(WoolClientWorldConfig.DEFAULT),
            (access, registryManager) -> access.set(WoolClientWorldConfig.load(registryManager))
    );

    private WoolConfig() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate WoolConfig!");
    }


    public static boolean serverDoesNotExist() {
        return Config.doesNotExist(WoolServerConfig.ID);
    }

    public static WoolServerConfig getServer() {
        return CONFIGS.getServer().orElse(WoolServerConfig.DEFAULT);
    }

    public static synchronized void saveServer() {
        getServer().save();
    }

    public static void reloadServer() {
        CONFIGS.reloadServer();
    }

    public static boolean serverWorldDoesNotExist() {
        return Config.doesNotExist(WoolServerWorldConfig.ID);
    }

    public static WoolServerWorldConfig getServerWorld() {
        return CONFIGS.getServerWorld().orElse(WoolServerWorldConfig.DEFAULT);
    }

    public static synchronized void saveServerWorld(DynamicRegistryManager registryManager) {
        getServerWorld().save(registryManager);
    }

    public static void reloadServerWorld(DynamicRegistryManager registryManager) {
        CONFIGS.reloadServerWorld(registryManager);
    }

    public static boolean clientDoesNotExist() {
        return Config.doesNotExist(WoolClientConfig.ID);
    }

    public static WoolClientConfig getClient() {
        return CONFIGS.getClient().orElse(WoolClientConfig.DEFAULT);
    }

    public static synchronized void saveClient() {
        getClient().save();
    }

    public static void reloadClient() {
        CONFIGS.reloadClient();
    }

    public static boolean clientWorldDoesNotExist() {
        return Config.doesNotExist(WoolClientWorldConfig.ID);
    }

    public static WoolClientWorldConfig getClientWorld() {
        return CONFIGS.getClientWorld().orElse(WoolClientWorldConfig.DEFAULT);
    }

    public static synchronized void saveClientWorld(DynamicRegistryManager registryManager) {
        getClientWorld().save(registryManager);
    }

    public static void reloadClientWorld(DynamicRegistryManager registryManager) {
        CONFIGS.reloadClientWorld(registryManager);
    }
}
