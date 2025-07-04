package net.mrwooly357.wool.entity.util;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.registry.WoolRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface AccessoryInventoryHolder {


    @Nullable
    Identifier getId();

    @Nullable
    Registry<AccessoryInventoryUnit> getRegistry();

    @Nullable
    Map<Identifier, AccessoryInventoryUnit> getFullAccessoryInventory();

    @Nullable
    Map<Identifier, AccessoryInventoryUnit> getAccessoryInventory();

    default boolean isValid() {
        return getRegistry() != null && getId() != null && WoolRegistries.ENTITY_ACCESSORY_INVENTORY.containsId(getId());
    }
}
