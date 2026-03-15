package net.mrwooly357.wool.config;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public final class ConfigManagersRegistry {

    private static final Set<ConfigManager.General<?>> GENERAL_CONFIG_MANAGERS = new HashSet<>();
    private static final Set<ConfigManager.World<?>> WORLD_CONFIG_MANAGERS = new HashSet<>();


    public static void registerGeneral(ConfigManager.General<?> manager) {
        GENERAL_CONFIG_MANAGERS.add(manager);
    }

    public static void registerWorld(ConfigManager.World<?> manager) {
        WORLD_CONFIG_MANAGERS.add(manager);
    }

    public static void forEachGeneral(Consumer<? super ConfigManager.General<?>> action) {
        GENERAL_CONFIG_MANAGERS.forEach(action);
    }

    public static void forEachWorld(Consumer<? super ConfigManager.World<?>> action) {
        WORLD_CONFIG_MANAGERS.forEach(action);
    }
}
