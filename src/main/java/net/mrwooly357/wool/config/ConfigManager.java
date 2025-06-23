package net.mrwooly357.wool.config;

import net.mrwooly357.wool.registry.WoolRegistries;

import java.util.List;

public class ConfigManager {

    private static final List<Config> configs = WoolRegistries.CONFIG.stream().toList();


    public static void loadAll() {

        for (Config config : configs) {
            config.load();
        }
    }

    public static void resetToDefaultAll() {

        for (Config config : configs) {
            config.resetToDefault();
        }
    }
}
