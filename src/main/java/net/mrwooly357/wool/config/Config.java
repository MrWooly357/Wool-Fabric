package net.mrwooly357.wool.config;

import com.google.common.collect.ImmutableList;
import net.fabricmc.loader.api.FabricLoader;
import net.mrwooly357.wool.config.field_type.FieldType;
import net.mrwooly357.wool.config.restriction.Restriction;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.WoolRegistries;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public abstract class Config {

    protected final String name;
    protected List<String> defaultLines = new ArrayList<>();
    protected List<String> lines = new ArrayList<>();
    protected final Map<String, Entry<?>> entries;

    protected Config(String name) {
        this.name = name;
        entries = new LinkedHashMap<>();
    }


    protected void emptyLine() {
        defaultLines.add("");
        lines.add("");
    }

    protected void comment(String comment) {
        String formattedComment = "# " + comment;

        defaultLines.add(formattedComment);
        lines.add(formattedComment);
    }

    protected void category(Entry.Category category) {
        String formattedCategory =  "# " + category.getName();

        defaultLines.add(formattedCategory);
        lines.add(formattedCategory);
    }

    protected void entry(Entry<?> entry) {
        entry(entry, null);
    }

    protected void entry(Entry<?> entry, @Nullable String comment) {
        if (comment != null)
            comment(comment);

        Restriction<?> restriction = entry.restriction;

        if (restriction != null)
            comment(restriction.getComment());

        String asString = entry.fullKey() + " = " + entry.getFormattedValue();

        defaultLines.add(asString);
        lines.add(asString);
        entries.put(entry.fullKey(), entry);
    }

    public void load() {
        Path configDirectory = FabricLoader.getInstance().getGameDir().resolve("wool").resolve("config");
        Path filePath = configDirectory.resolve(name + ".txt");
        Map<String, String> existing = new HashMap<>();

        if (Files.exists(filePath)) {

            try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
                String line;

                while ((line = reader.readLine()) != null) {

                    if (line.isBlank() || line.startsWith("#"))
                        continue;

                    line = line.trim();
                    int equals = line.indexOf('=');

                    if (equals <= 0)
                        continue;

                    existing.put(line.substring(0, equals).trim(), line.substring(equals + 1).trim());
                }
            } catch (IOException exception) {

                if (WoolConfig.enableDeveloperMode)
                    Wool.LOGGER.error("An error occurred while reading a config {}: {}", filePath, exception.getMessage());
            }
        }

        try {
            Files.createDirectories(configDirectory);
        } catch (IOException exception) {

            if (WoolConfig.enableDeveloperMode)
                Wool.LOGGER.error("Can't to create config directory {}: {}", configDirectory, exception.getMessage());
        }

        List<String> outputLines = new ArrayList<>();

        for (String line : lines) {

            if (line.isBlank() || line.startsWith("#")) {
                outputLines.add(line);

                continue;
            }

            String[] split = line.split("=", 2);

            if (split.length != 2)
                continue;

            String key = split[0].trim();
            Entry<?> entry = entries.get(key);

            if (entries.containsKey(key) && existing.containsKey(key))
                entry.parseAndSetValue(existing.get(key));

            outputLines.add(key + " = " + entry.getFormattedValue());
        }

        onUpdate();

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {

            for (String line : outputLines) {
                writer.write(line);
                writer.newLine();
            }

            lines = outputLines;
        } catch (IOException exception) {

            if (WoolConfig.enableDeveloperMode)
                Wool.LOGGER.error("An error occurred while writing a config {}: {}", filePath, exception.getMessage());
        }
    }

    public void resetToDefault() {
        Path configDirectory = FabricLoader.getInstance().getGameDir().resolve("wool").resolve("config");
        Path filePath = configDirectory.resolve(name + ".txt");

        try {
            Files.createDirectories(configDirectory);
        } catch (IOException exception) {

            if (WoolConfig.enableDeveloperMode)
                Wool.LOGGER.error("Can't create config directory {}: {}", configDirectory, exception.getMessage());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {

            for (String line : defaultLines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException exception) {

            if (WoolConfig.enableDeveloperMode)
                Wool.LOGGER.error("An error occurred while writing a config {}: {}", filePath, exception.getMessage());
        }
    }

    protected abstract void onUpdate();


    public static final class Entry<T> {

        @Nullable
        private final Category category;
        private final String key;
        private final FieldType<T> type;
        @Nullable
        private final Restriction<T> restriction;
        private T value;

        private Entry(@Nullable Category category, String key, FieldType<T> type, @Nullable Restriction<T> restriction, T defaultValue) {
            this.category = category;
            this.key = key;
            this.type = type;
            this.restriction = restriction;
            value = defaultValue;
        }


        public String fullKey() {
            return category == null ? key : category.getName() + "." + key;
        }

        public T getValue() {
            return value;
        }

        public String getFormattedValue() {
            return type.format(value);
        }

        public void parseAndSetValue(String string) {
            T raw = type.parse(string);

            if (restriction != null) {
                value = restriction.normalize(raw);
            } else
                value = raw;
        }

        public static <T> Builder<T> builder() {
            return new Builder<>();
        }


        public static final class Builder<T> {

            @Nullable
            private Category category;
            @Nullable
            private Restriction<T> restriction;

            private Builder() {}


            public Builder<T> category(Category category) {
                this.category = category;

                return this;
            }

            public Builder<T> restriction(Restriction<T> restriction) {
                this.restriction = restriction;

                return this;
            }

            public Entry<T> build(String key, FieldType<T> type, T defaultValue) {
                return new Entry<>(category, key, type, restriction, defaultValue);
            }
        }


        public static final class Category {

            private final String name;
            @Nullable
            private final ImmutableList<Category> categories;

            private Category(String name, @Nullable ImmutableList<Category> categories) {
                this.name = name;
                this.categories = categories;
            }


            public String getName() {
                String name = this.name;

                if (categories != null) {
                    List<String> additionalCategories = new ArrayList<>();
                    StringBuilder builder = new StringBuilder();

                    for (Category category : categories)
                        additionalCategories.add(category.getName());

                    for (String additionalCategory : additionalCategories)
                        builder.append(additionalCategory).append(".");

                    builder.append(name);

                    name = builder.toString();
                }

                return name;
            }

            public static Builder builder() {
                return new Builder();
            }


            public static final class Builder {

                private ImmutableList<Category> categories;

                private Builder() {}


                public Builder categories(ImmutableList<Category> categories) {
                    this.categories = categories;

                    return this;
                }

                public Category build(String name) {
                    return new Category(name, categories);
                }
            }
        }
    }


    public static final class Manager {

        private static final List<Config> CONFIGS = WoolRegistries.CONFIG.stream().toList();


        public static void loadAll() {
            for (Config config : CONFIGS)
                config.load();
        }

        public static void resetToDefaultAll() {
            for (Config config : CONFIGS)
                config.resetToDefault();
        }
    }
}
