package net.mrwooly357.wool.config.custom.wool;

import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.Config;

import java.util.List;
import java.util.Map;

public final class WoolClientConfig extends Config<WoolClientConfig> {

    public static final Identifier ID = Wool.id("client");
    public static final Codec<WoolClientConfig> CODEC = Category.createCodec(Map.of())
            .xmap(category -> new WoolClientConfig(category.categories(), category.options()), config -> config.root);
    public static final WoolClientConfig DEFAULT = new WoolClientConfig(Map.of());

    private WoolClientConfig(Map<String, Object> options, Category... categories) {
        super(options, categories);
    }

    private WoolClientConfig(List<Category> categories, Map<String, Object> options) {
        super(categories, options);
    }


    @Override
    protected Identifier getId() {
        return ID;
    }

    @Override
    protected Codec<WoolClientConfig> getCodec() {
        return CODEC;
    }

    public void save() {
        serialize(JsonOps.INSTANCE);
    }

    public static WoolClientConfig load() {
        return deserialize(ID, CODEC, JsonOps.INSTANCE, DEFAULT);
    }
}
