package net.mrwooly357.wool.config.custom.wool;

import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.Config;

import java.util.List;
import java.util.Map;

public final class WoolServerWorldConfig extends Config<WoolServerWorldConfig> {

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

    public void save(DynamicRegistryManager registryManager) {
        serialize(registryManager.getOps(JsonOps.INSTANCE));
    }

    public static WoolServerWorldConfig load(DynamicRegistryManager registryManager) {
        return deserialize(ID, CODEC, registryManager.getOps(JsonOps.INSTANCE), DEFAULT);
    }
}
