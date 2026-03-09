package net.mrwooly357.wool.util.collection.map;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public final class MapUtil {

    private MapUtil() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate MapUtil!");
    }


    public static <K, V> Map<K, V> copyWith(Supplier<Map<K, V>> temporaryMapSupplier, Function<Map<K, V>, Map<K, V>> factory, Map<K, V> map, Map<K, V> newElements) {
        Map<K, V> temporary = temporaryMapSupplier.get();
        temporary.putAll(map);
        temporary.putAll(newElements);

        return factory.apply(temporary);
    }

    public static <K, V> Map<K, V> immutableCopyWith(Map<K, V> map, Map<K, V> newElements) {
        return copyWith(HashMap::new, Map::copyOf, map, newElements);
    }
}
