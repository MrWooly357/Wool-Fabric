package net.mrwooly357.wool.config;

import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.EmptyConfig;
import net.mrwooly357.wool.registry.ConfigRegistryHelper;

public class Configs {

    public static final Config EMPTY = register(
            "empty", new EmptyConfig()
    );


    private static Config register(String name, Config config) {
        return ConfigRegistryHelper.register(Identifier.of(Wool.MOD_ID, name), config);
    }
}
