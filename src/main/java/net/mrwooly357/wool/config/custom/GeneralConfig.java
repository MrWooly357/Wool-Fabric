package net.mrwooly357.wool.config.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.config.Config;

import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Map;

public abstract class GeneralConfig extends Config {

    protected GeneralConfig(Category... categories) {
        super(categories);
    }

    protected GeneralConfig(Map<String, Object> options, Category... categories) {
        super(options, categories);
    }

    protected GeneralConfig(List<Category> categories, Map<String, Object> options) {
        super(categories, options);
    }


    @Override
    protected abstract Codec<? extends GeneralConfig> getCodec();

    public final void save() {
        serialize(getId(), "", this, JsonOps.INSTANCE);
    }

    @SuppressWarnings("unchecked")
    protected static <C extends GeneralConfig> C deserialize(Identifier id, Codec<C> codec, GeneralConfig fallback) {
        try {
            return (C) deserialize(id, "", codec, JsonOps.INSTANCE, fallback);
        } catch (NoSuchFileException e) {
            throw new RuntimeException("Failed to decode config " + id + "!", e);
        }
    }
}
