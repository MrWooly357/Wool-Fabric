package net.mrwooly357.wool.registry.custom;

import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class CustomBoatsRegistry {

    private static final Map<BoatEntity.Type, Item> TO_ITEM = new HashMap<>();

    private CustomBoatsRegistry() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate CustomBoatsRegistry!");
    }


    public static void register(BoatEntity.Type type, Item item) {
        TO_ITEM.put(type, item);
    }

    public static Optional<Item> getAsItem(BoatEntity.Type type) {
        return Optional.ofNullable(TO_ITEM.get(type));
    }
}
