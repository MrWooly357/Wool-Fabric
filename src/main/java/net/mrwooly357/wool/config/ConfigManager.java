package net.mrwooly357.wool.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private static final List<Config> configs = new ArrayList<>();


    public static void register(Config config) {
        if (!configs.contains(config)) {
            configs.add(config);
            config.reload();
        }
    }

    public static void reloadAll() {
        for (Config config : configs) {
            config.reload();
        }
    }

    public static void saveAll() {
        for (Config config : configs) {
            config.save();
        }
    }
}
