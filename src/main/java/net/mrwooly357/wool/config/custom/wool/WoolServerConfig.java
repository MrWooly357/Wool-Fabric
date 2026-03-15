package net.mrwooly357.wool.config.custom.wool;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.GeneralConfig;

import java.util.List;
import java.util.Map;

public final class WoolServerConfig extends GeneralConfig {

    public static final Identifier ID = Wool.id("server");
    public static final Codec<WoolServerConfig> CODEC = Category.createCodec(Map.of())
            .xmap(category -> new WoolServerConfig(category.categories(), category.options()), config -> config.root);
    public static final WoolServerConfig DEFAULT = new WoolServerConfig(Map.of());

    private WoolServerConfig(Map<String, Object> options, Category... categories) {
        super(options, categories);
    }

    private WoolServerConfig(List<Category> categories, Map<String, Object> options) {
        super(categories, options);
    }


    @Override
    protected Identifier getId() {
        return ID;
    }

    @Override
    protected Codec<WoolServerConfig> getCodec() {
        return CODEC;
    }

    public static boolean doesNotExist() {
        return doesNotExist(ID, "");
    }

    public static WoolServerConfig load() {
        return deserialize(ID, CODEC, DEFAULT);
    }
}
