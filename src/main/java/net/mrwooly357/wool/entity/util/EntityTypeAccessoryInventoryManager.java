package net.mrwooly357.wool.entity.util;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WoolConfig;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EntityTypeAccessoryInventoryManager {

    private static Map<EntityType<?>, Registry<AccessoryInventoryUnit>> entityTypeToRegistry;
    private static Map<Registry<AccessoryInventoryUnit>, Identifier> registryToId;
    private static Map<Registry<AccessoryInventoryUnit>, List<Identifier>> unitOrder;


    public static Map<EntityType<?>, Registry<AccessoryInventoryUnit>> getEntityTypeToRegistry() {
        return entityTypeToRegistry;
    }

    public static Map<Registry<AccessoryInventoryUnit>, Identifier> getRegistryToId() {
        return registryToId;
    }

    public static Map<Registry<AccessoryInventoryUnit>, List<Identifier>> getUnitOrder() {
        return unitOrder;
    }

    public static void addToEntityTypeToRegistry(EntityType<?> type, Registry<AccessoryInventoryUnit> registry) {
        entityTypeToRegistry.put(type, registry);
    }

    public static void addToRegistryTyRegistryKey(Registry<AccessoryInventoryUnit> registry, Identifier id) {
        registryToId.put(registry, id);
    }

    public static void addToUnitOrder(Registry<AccessoryInventoryUnit> registry, List<Identifier> order) {
        unitOrder.put(registry, order);
    }

    public static void initialize() {
        entityTypeToRegistry = new LinkedHashMap<>();
        registryToId = new LinkedHashMap<>();
        unitOrder = new LinkedHashMap<>();

        if (WoolConfig.developerMode)
            Wool.LOGGER.info("Initializing entity type accessory inventory manager");
    }
}
