package net.mrwooly357.wool.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.mrwooly357.wool.Wool;

public final class WoolRegistries {

    private WoolRegistries() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate WoolRegistries!");
    }


    private static <A> Registry<A> of(RegistryKey<Registry<A>> key) {
        return FabricRegistryBuilder.createSimple(key)
                .attribute(RegistryAttribute.SYNCED)
                .buildAndRegister();
    }

    public static void initialize() {
        Wool.logInitialization("registries");
    }
}
