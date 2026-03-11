package net.mrwooly357.wool.registry;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.mrwooly357.wool.Wool;

public final class WoolRegistryKeys {

    private WoolRegistryKeys() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate WoolRegistryKeys!");
    }


    private static <A> RegistryKey<Registry<A>> of(String id) {
        return RegistryKey.ofRegistry(Wool.id(id));
    }
}
