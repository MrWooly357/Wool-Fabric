package net.mrwooly357.wool.config.custom.wool;

import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.Config;
import net.mrwooly357.wool.config.ConfigManager;
import net.mrwooly357.wool.config.ConfigManagersRegistry;
import net.mrwooly357.wool.config.custom.GeneralConfig;
import net.mrwooly357.wool.config.custom.WorldConfig;

import java.util.HashMap;
import java.util.Map;

public final class WoolConfig {

    private static final GeneralConfig.Instance<WoolServerConfig> SERVER = new GeneralConfig.Instance<>(WoolServerConfig.DEFAULT);
    private static final Map<String, Config.Instance<WoolServerWorldConfig>> SERVER_WORLD = new HashMap<>();
    private static final GeneralConfig.Instance<WoolClientConfig> CLIENT = new GeneralConfig.Instance<>(WoolClientConfig.DEFAULT);
    private static final Map<String, WorldConfig.Instance<WoolClientWorldConfig>> CLIENT_WORLD = new HashMap<>();

    private WoolConfig() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate WoolConfig!");
    }


    public static void register() {
        Wool.logInitialization("config");
        ConfigManagersRegistry.registerGeneralServer(Wool.MOD_ID, new ConfigManager.General<>(
                WoolServerConfig::doesNotExist,
                WoolServerConfig::save,
                SERVER,
                WoolServerConfig::load)
        );
        ConfigManagersRegistry.registerServerWorld(Wool.MOD_ID, new ConfigManager.World<>(
                WoolServerWorldConfig::doesNotExist,
                WoolServerWorldConfig::save,
                SERVER_WORLD,
                WoolServerWorldConfig::load,
                WoolServerWorldConfig.DEFAULT,
                WoolServerWorldConfig::delete
        ));
        ConfigManagersRegistry.registerGeneralClient(Wool.MOD_ID, new ConfigManager.General<>(
                WoolClientConfig::doesNotExist,
                WoolClientConfig::save, CLIENT,
                WoolClientConfig::load
        ));
        ConfigManagersRegistry.registerClientWorld(Wool.MOD_ID, new ConfigManager.World<>(
                WoolClientWorldConfig::doesNotExist,
                WoolClientWorldConfig::save,
                CLIENT_WORLD,
                WoolClientWorldConfig::load,
                WoolClientWorldConfig.DEFAULT,
                WoolClientWorldConfig::delete
        ));
    }

    public static WoolServerConfig getServer() {
        return SERVER.get();
    }

    public static WoolServerWorldConfig getServerWorld(String name) {
        return SERVER_WORLD.computeIfAbsent(name, s -> new WorldConfig.Instance<>(WoolServerWorldConfig.DEFAULT)).get();
    }

    public static WoolClientConfig getClient() {
        return CLIENT.get();
    }

    public static WoolClientWorldConfig getClientWorld(String name) {
        return CLIENT_WORLD.computeIfAbsent(name, s -> new WorldConfig.Instance<>(WoolClientWorldConfig.DEFAULT)).get();
    }
}
