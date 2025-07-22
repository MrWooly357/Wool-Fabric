package net.mrwooly357.wool.config;

import net.minecraft.util.Identifier;
import net.mrwooly357.wool.registry.WoolRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {

    private static final List<Config> configs = WoolRegistries.CONFIG.stream().toList();
    private static final Map<Identifier, Config> idsToConfigs = new HashMap<>();


    public static Map<Identifier, Config> getIdsToConfigs() {
        return idsToConfigs;
    }

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
