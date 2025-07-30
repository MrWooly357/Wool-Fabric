package net.mrwooly357.wool.accessory.entity.inventory;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WoolConfig;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface AccessoryInventoryManager {

    Map<EntityType<?>, Registry<AccessoryInventoryUnit>> ENTITY_TYPE_TO_REGISTRY = new LinkedHashMap<>();
    Map<Registry<AccessoryInventoryUnit>, Identifier> REGISTRY_TO_ID = new LinkedHashMap<>();
    Map<Registry<AccessoryInventoryUnit>, List<Identifier>> UNIT_ORDER = new LinkedHashMap<>();


    static void initialize() {
        if (WoolConfig.enableDeveloperMode)
            Wool.LOGGER.info("Initializing accessory inventory manager");
    }
}
