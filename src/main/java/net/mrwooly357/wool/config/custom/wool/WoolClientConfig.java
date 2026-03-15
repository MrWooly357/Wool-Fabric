package net.mrwooly357.wool.config.custom.wool;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.GeneralConfig;

import java.util.List;
import java.util.Map;

public final class WoolClientConfig extends GeneralConfig {

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

    public static boolean doesNotExist() {
        return doesNotExist(ID, "");
    }

    public static WoolClientConfig load() {
        return deserialize(ID, CODEC, DEFAULT);
    }
}
