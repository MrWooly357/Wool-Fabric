package net.mrwooly357.wool.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public abstract class Config<C extends Config<C>> {

    private static final Logger LOGGER = Wool.LOGGER;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    protected final Category root;

    protected Config(Category... categories) {
        this(Map.of(), categories);
    }

    protected Config(Map<String, Object> options, Category... categories) {
        this(List.of(categories), options);
    }

    protected Config(List<Category> categories, Map<String, Object> options) {
        root = new Category(categories, options);
    }


    protected abstract Identifier getId();

    protected abstract Codec<C> getCodec();

    @SuppressWarnings("unchecked")
    protected final void serialize(DynamicOps<JsonElement> ops) {
        Identifier id = getId();
        Path directoryPath = getDirectoryPath(id.getNamespace());

        if (Files.notExists(directoryPath))
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create directory " + directoryPath + " for config " + id, e);
            }

        getCodec().encodeStart(ops, (C) this)
                .ifError(error -> LOGGER.error("Failed to encode config {} {}", id, error.message()))
                .ifSuccess(json -> {
                    Path path = getPath(directoryPath, id);

                    try (Writer writer = Files.newBufferedWriter(path)) {
                        GSON.toJson(json, writer);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to write config " + id + "!", e);
                    }
                });
    }

    protected static <C extends Config<C>> C deserialize(Identifier id, Codec<C> codec, DynamicOps<JsonElement> ops, C fallback) {
        Path directoryPath = getDirectoryPath(id.getNamespace());

        if (Files.notExists(directoryPath))
            return fallback;

        Path path = getPath(directoryPath, id);

        if (Files.exists(path))
            try (Reader reader = Files.newBufferedReader(path)) {
                return codec.parse(ops, JsonParser.parseReader(reader))
                        .ifError(error -> LOGGER.error("Failed to decode config {} {}", id, error.message()))
                        .resultOrPartial()
                        .orElse(fallback);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read config " + id + "!", e);
            }
        else
            return fallback;
    }

    public static boolean doesNotExist(Identifier id) {
        return Files.notExists(getPath(getDirectoryPath(id.getNamespace()), id));
    }

    private static Path getDirectoryPath(String modId) {
        return FabricLoader.getInstance().getConfigDir()
                .resolve(modId);
    }

    private static Path getPath(Path directoryPath, Identifier id) {
        return directoryPath.resolve(id.toString().replace(":", "-") + ".json");
    }


    public record Category(List<Category> categories, Map<String, Object> options) {


        @SuppressWarnings("unchecked")
        public static Codec<Category> createCodec(Map<String, Codec<?>> optionCodecs) {
            return Codec.recursive("Config.Category", codec -> RecordCodecBuilder.create(
                    instance -> instance.group(
                            codec.listOf().optionalFieldOf("categories", List.of()).forGetter(Category::categories),
                            Codec.dispatchedMap(Codec.STRING, id -> (Codec<Object>) optionCodecs.get(id)).optionalFieldOf("options", Map.of()).forGetter(category -> category.options)
                    )
                            .apply(instance, Category::new)
            ));
        }

        @Override
        public @NotNull String toString() {
            return "Config.Category[categories: " + categories
                    + ", options: " + options + "]";
        }
    }
}
