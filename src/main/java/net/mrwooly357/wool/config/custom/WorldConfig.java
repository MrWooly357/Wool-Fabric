package net.mrwooly357.wool.config.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.config.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Map;

public abstract class WorldConfig extends Config {

    protected WorldConfig(Category... categories) {
        super(categories);
    }

    protected WorldConfig(Map<String, Object> options, Category... categories) {
        super(options, categories);
    }

    protected WorldConfig(List<Category> categories, Map<String, Object> options) {
        super(categories, options);
    }


    @Override
    protected abstract Codec<? extends WorldConfig> getCodec();

    public final void save(String name, DynamicRegistryManager registryManager) {
        serialize(getId(), name, this, registryManager.getOps(JsonOps.INSTANCE));
    }

    @SuppressWarnings("unchecked")
    protected static <C extends WorldConfig> C deserialize(Identifier id, String name, Codec<C> codec, DynamicRegistryManager registryManager, WorldConfig fallback) {
        try {
            return (C) deserialize(id, name, codec, registryManager.getOps(JsonOps.INSTANCE), fallback);
        } catch (NoSuchFileException e) {
            throw new RuntimeException("Failed to decode config " + id + "!", e);
        }
    }

    protected static void delete(Identifier id, String suffix) {
        String id1 = suffix.isEmpty() ? id.toString() : id + "-" + suffix;

        try {
            Files.deleteIfExists(getPath(getDirectoryPath(id), id1));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete config: " + id1 + "!", e);
        }
    }
}
