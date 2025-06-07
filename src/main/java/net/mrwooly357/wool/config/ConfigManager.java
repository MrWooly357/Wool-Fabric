package net.mrwooly357.wool.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private static final List<Config> configs = new ArrayList<>();


    public static void register(Config config) {
        if (!configs.contains(config)) {
            configs.add(config);
            config.load();
        }
    }

    public static void loadAll() {
        for (Config config : configs) {
            config.load();
        }
    }

    public static void saveAll() {
        for (Config config : configs) {
            config.save();
        }
    }

    public static void resetAll() {
        for (Config config : configs) {
            config.reset();
        }
    }

    public static void resetToDefaultAll() {
        for (Config config : configs) {
            config.resetToDefault();
        }
    }
}
