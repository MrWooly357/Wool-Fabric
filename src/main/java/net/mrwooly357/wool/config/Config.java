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
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public abstract class Config {

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

    protected abstract Codec<? extends Config> getCodec();

    protected static boolean doesNotExist(Identifier id, String suffix) {
        return Files.notExists(getPath(getDirectoryPath(id), suffix), LinkOption.NOFOLLOW_LINKS);
    }

    @SuppressWarnings("unchecked")
    protected static void serialize(Identifier id, String suffix, Config config, DynamicOps<JsonElement> ops) {
        Path directoryPath = getDirectoryPath(id);
        String id1 = suffix.isEmpty() ? id.toString() : id + "-" + suffix;

        if (Files.notExists(directoryPath))
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create directory " + directoryPath + " for config " + id1, e);
            }

        ((Codec<Config>) config.getCodec()).encodeStart(ops, config)
                .ifError(error -> LOGGER.error("Failed to encode config {} {}", id1, error.message()))
                .ifSuccess(json -> {
                    Path path = getPath(directoryPath, id1);

                    try (Writer writer = Files.newBufferedWriter(path)) {
                        GSON.toJson(json, writer);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to write config " + id1 + "!", e);
                    }
                });
    }

    @SuppressWarnings("unchecked")
    protected static Config deserialize(Identifier id, String suffix, Codec<? extends Config> codec, DynamicOps<JsonElement> ops, Config fallback) throws NoSuchFileException {
        Path directoryPath = getDirectoryPath(id);
        String id1 = suffix.isEmpty() ? id.toString() : id + "-" + suffix;

        if (Files.notExists(directoryPath))
            throw new NoSuchFileException("Config " + id1 + " file not found!");

        Path path = getPath(directoryPath, id1);

        if (Files.exists(path))
            try (Reader reader = Files.newBufferedReader(path)) {
                return ((Codec<Config>) codec).parse(ops, JsonParser.parseReader(reader))
                        .ifError(error -> LOGGER.error("Failed to decode config {} {}!", id1, error.message()))
                        .resultOrPartial()
                        .orElse(fallback);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read config " + id1 + "!", e);
            }
        else
            return fallback;
    }

    protected static Path getDirectoryPath(Identifier id) {
        return FabricLoader.getInstance().getConfigDir()
                .resolve(id.getNamespace());
    }

    protected static Path getPath(Path directoryPath, String id) {
        return directoryPath.resolve(id.replaceAll("[^\\p{L}\\p{N}._-]", "-") + ".json");
    }

    @SuppressWarnings("unchecked")
    protected <A> A get(Category category, String id) {
        return (A) category.options.get(id);
    }

    @Override
    public int hashCode() {
        return root.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) || (other instanceof Config config
                && root.equals(config.root));
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


    public static final class Instance<C extends Config> {

        private volatile C config;

        public Instance(C initial) {
            config = initial;
        }


        public C get() {
            return config;
        }

        public void set(C config) {
            this.config = config;
        }

        @Override
        public int hashCode() {
            return config.hashCode();
        }

        @Override
        public boolean equals(Object other) {
            return super.equals(other) || (other instanceof Instance<?> instance
                    && config.equals(instance.config));
        }

        @Override
        public String toString() {
            return "Config.Instance[config: " + config + "]";
        }
    }
}
