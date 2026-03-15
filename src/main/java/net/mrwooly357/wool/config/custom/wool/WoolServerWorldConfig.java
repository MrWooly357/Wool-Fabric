package net.mrwooly357.wool.config.custom.wool;

import com.mojang.serialization.Codec;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WorldConfig;

import java.util.List;
import java.util.Map;

public final class WoolServerWorldConfig extends WorldConfig {

    public static final Identifier ID = Wool.id("server_world");
    public static final Codec<WoolServerWorldConfig> CODEC = Category.createCodec(Map.of())
            .xmap(category -> new WoolServerWorldConfig(category.categories(), category.options()), config -> config.root);
    public static final WoolServerWorldConfig DEFAULT = new WoolServerWorldConfig(Map.of());

    private WoolServerWorldConfig(Map<String, Object> options, Category... categories) {
        super(options, categories);
    }

    private WoolServerWorldConfig(List<Category> categories, Map<String, Object> options) {
        super(categories, options);
    }


    @Override
    protected Identifier getId() {
        return ID;
    }

    @Override
    protected Codec<WoolServerWorldConfig> getCodec() {
        return CODEC;
    }

    public static boolean doesNotExist(String name) {
        return doesNotExist(ID, name);
    }

    public static WoolServerWorldConfig load(String name, DynamicRegistryManager registryManager) {
        return deserialize(ID, name, CODEC, registryManager, DEFAULT);
    }

    public static void delete(String name) {
        delete(ID, name);
    }
}
