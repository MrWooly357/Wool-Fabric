package net.mrwooly357.wool.config;

import net.minecraft.registry.DynamicRegistryManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public final class ConfigManagersRegistry {

    private static final Map<String, ConfigManager.General<?>> GENERAL_SERVER_CONFIG_MANAGERS = new ConcurrentHashMap<>();
    private static final Map<String, ConfigManager.General<?>> GENERAL_CLIENT_CONFIG_MANAGERS = new ConcurrentHashMap<>();
    private static final Map<String, ConfigManager.World<?>> SERVER_WORLD_CONFIG_MANAGERS = new ConcurrentHashMap<>();
    private static final Map<String, ConfigManager.World<?>> CLIENT_WORLD_CONFIG_MANAGERS = new ConcurrentHashMap<>();

    private ConfigManagersRegistry() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate ConfigManagersRegistry!");
    }


    public static void registerGeneralServer(String modId, ConfigManager.General<?> manager) {
        GENERAL_SERVER_CONFIG_MANAGERS.put(modId, manager);
    }

    public static void registerGeneralClient(String modId, ConfigManager.General<?> manager) {
        GENERAL_CLIENT_CONFIG_MANAGERS.put(modId, manager);
    }

    public static void registerServerWorld(String modId, ConfigManager.World<?> manager) {
        SERVER_WORLD_CONFIG_MANAGERS.put(modId, manager);
    }

    public static void registerClientWorld(String modId, ConfigManager.World<?> manager) {
        CLIENT_WORLD_CONFIG_MANAGERS.put(modId, manager);
    }

    public static Stream<String> getAllGeneralServerIds() {
        return GENERAL_SERVER_CONFIG_MANAGERS.keySet().stream();
    }

    public static Stream<String> getAllGeneralClientIds() {
        return GENERAL_CLIENT_CONFIG_MANAGERS.keySet().stream();
    }

    public static Stream<String> getAllServerWorldIds() {
        return SERVER_WORLD_CONFIG_MANAGERS.keySet().stream();
    }

    public static Stream<String> getAllClientWorldIds() {
        return CLIENT_WORLD_CONFIG_MANAGERS.keySet().stream();
    }

    public static void saveGeneralServer(String id) {
        GENERAL_SERVER_CONFIG_MANAGERS.get(id).save();
    }

    public static void saveGeneralClient(String id) {
        GENERAL_CLIENT_CONFIG_MANAGERS.get(id).save();
    }

    public static void saveServerWorld(String id, String name, DynamicRegistryManager registryManager) {
        SERVER_WORLD_CONFIG_MANAGERS.get(id).save(name, registryManager);
    }

    public static void saveClientWorld(String id, String name, DynamicRegistryManager registryManager) {
        CLIENT_WORLD_CONFIG_MANAGERS.get(id).save(name, registryManager);
    }

    public static void loadGeneralServer(String id) {
        GENERAL_SERVER_CONFIG_MANAGERS.get(id).load();
    }

    public static void loadGeneralClient(String id) {
        GENERAL_CLIENT_CONFIG_MANAGERS.get(id).load();
    }

    public static void loadServerWorld(String id, String name, DynamicRegistryManager registryManager) {
        SERVER_WORLD_CONFIG_MANAGERS.get(id).load(name, registryManager);
    }

    public static void loadClientWorld(String id, String name, DynamicRegistryManager registryManager) {
        CLIENT_WORLD_CONFIG_MANAGERS.get(id).load(name, registryManager);
    }

    public static void forEachGeneralServer(BiConsumer<? super String, ? super ConfigManager.General<?>> action) {
        GENERAL_SERVER_CONFIG_MANAGERS.forEach(action);
    }

    public static void forEachGeneralClient(BiConsumer<? super String, ? super ConfigManager.General<?>> action) {
        GENERAL_CLIENT_CONFIG_MANAGERS.forEach(action);
    }

    public static void forEachServerWorld(BiConsumer<? super String, ? super ConfigManager.World<?>> action) {
        SERVER_WORLD_CONFIG_MANAGERS.forEach(action);
    }

    public static void forEachClientWorld(BiConsumer<? super String, ? super ConfigManager.World<?>> action) {
        CLIENT_WORLD_CONFIG_MANAGERS.forEach(action);
    }
}
