package net.mrwooly357.wool.config.custom.wool;

import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.Config;

import java.util.List;
import java.util.Map;

public final class WoolClientWorldConfig extends Config<WoolClientWorldConfig> {

    public static final Identifier ID = Wool.id("client_world");
    public static final Codec<WoolClientWorldConfig> CODEC = Category.createCodec(Map.of())
            .xmap(category -> new WoolClientWorldConfig(category.categories(), category.options()), config -> config.root);
    public static final WoolClientWorldConfig DEFAULT = new WoolClientWorldConfig(Map.of());

    private WoolClientWorldConfig(Map<String, Object> options, Category... categories) {
        super(options, categories);
    }

    private WoolClientWorldConfig(List<Category> categories, Map<String, Object> options) {
        super(categories, options);
    }


    @Override
    protected Identifier getId() {
        return ID;
    }

    @Override
    protected Codec<WoolClientWorldConfig> getCodec() {
        return CODEC;
    }

    public void save(DynamicRegistryManager registryManager) {
        serialize(registryManager.getOps(JsonOps.INSTANCE));
    }

    public static WoolClientWorldConfig load(DynamicRegistryManager registryManager) {
        return deserialize(ID, CODEC, registryManager.getOps(JsonOps.INSTANCE), DEFAULT);
    }
}
