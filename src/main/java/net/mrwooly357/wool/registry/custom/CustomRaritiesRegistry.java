package net.mrwooly357.wool.registry.custom;

import net.minecraft.util.Rarity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class CustomRaritiesRegistry {

    private static final Map<Rarity, Rarity> TO_RARITY_WHEN_ENCHANTED = new HashMap<>();

    private CustomRaritiesRegistry() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate RaritiesRegistry!");
    }


    public static void register(Rarity rarity, Rarity whenEnchanted) {
        TO_RARITY_WHEN_ENCHANTED.put(rarity, whenEnchanted);
    }

    public static Optional<Rarity> getWhenEnchanted(Rarity rarity) {
        return Optional.ofNullable(TO_RARITY_WHEN_ENCHANTED.get(rarity));
    }
}
