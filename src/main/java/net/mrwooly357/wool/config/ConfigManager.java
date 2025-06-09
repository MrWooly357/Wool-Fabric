package net.mrwooly357.wool.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private static final List<Config> configs = new ArrayList<>();
    private static final List<String> configsAsStrings = new ArrayList<>();


    public static void register(Config config) {

        if (!configs.contains(config)) {
            configs.add(config);
            configsAsStrings.add(config.fileName);
            config.load();
        }
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
