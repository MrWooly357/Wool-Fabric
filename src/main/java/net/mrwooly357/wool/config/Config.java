package net.mrwooly357.wool.config;

import net.fabricmc.loader.api.FabricLoader;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.config.field_type.FieldType;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public abstract class Config {

    protected final String fileName;
    protected List<String> defaultLines = new ArrayList<>();
    protected List<String> previousLines;
    protected List<String> lines = new ArrayList<>();
    protected final Map<String, ConfigEntry<?>> entries = new LinkedHashMap<>();

    protected Config(String fileName) {
        this.fileName = fileName;
    }


    protected ConfigEntry<Integer> intField(String key, int defaultValue) {
        return intField(null, null, key, defaultValue);
    }

    protected ConfigEntry<Integer> intField(ConfigEntry.Category category, String key, int defaultValue) {
        return intField(null, category, key, defaultValue);
    }

    protected ConfigEntry<Integer> intField(String comment, String key, int defaultValue) {
        return intField(comment, null, key, defaultValue);
    }

    protected ConfigEntry<Integer> intField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, int defaultValue) {
        ConfigEntry<Integer> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.INTEGER);

        addComment(comment);
        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<Short> shortField(String key, short defaultValue) {
        return shortField(null, null, key, defaultValue);
    }

    protected ConfigEntry<Short> shortField(ConfigEntry.Category category, String key, short defaultValue) {
        return shortField(null, category, key, defaultValue);
    }

    protected ConfigEntry<Short> shortField(String comment, String key, short defaultValue) {
        return shortField(comment, null, key, defaultValue);
    }

    protected ConfigEntry<Short> shortField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, short defaultValue) {
        ConfigEntry<Short> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.SHORT);

        addComment(comment);
        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<Long> longField(String key, long defaultValue) {
        return longField(null, null, key, defaultValue);
    }

    protected ConfigEntry<Long> longField(ConfigEntry.Category category, String key, long defaultValue) {
        return longField(null, category, key, defaultValue);
    }

    protected ConfigEntry<Long> longField(String comment, String key, long defaultValue) {
        return longField(comment, null, key, defaultValue);
    }

    protected ConfigEntry<Long> longField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, long defaultValue) {
        ConfigEntry<Long> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.LONG);

        addComment(comment);
        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<Float> floatField(String key, float defaultValue) {
        return floatField(null, null, key, defaultValue);
    }

    protected ConfigEntry<Float> floatField(ConfigEntry.Category category, String key, float defaultValue) {
        return floatField(null, category, key, defaultValue);
    }

    protected ConfigEntry<Float> floatField(String comment, String key, float defaultValue) {
        return floatField(comment, null, key, defaultValue);
    }

    protected ConfigEntry<Float> floatField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, float defaultValue) {
        ConfigEntry<Float> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.FLOAT);

        addComment(comment);
        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<Double> doubleField(String key, double defaultValue) {
        return doubleField(null, null, key, defaultValue);
    }

    protected ConfigEntry<Double> doubleField(ConfigEntry.Category category, String key, double defaultValue) {
        return doubleField(null, category, key, defaultValue);
    }

    protected ConfigEntry<Double> doubleField(String comment, String key, double defaultValue) {
        return doubleField(comment, null, key, defaultValue);
    }

    protected ConfigEntry<Double> doubleField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, double defaultValue) {
        ConfigEntry<Double> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.DOUBLE);

        addComment(comment);
        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<Boolean> booleanField(String key, boolean defaultValue) {
        return booleanField(null, null, key, defaultValue);
    }

    protected ConfigEntry<Boolean> booleanField(ConfigEntry.Category category, String key, boolean defaultValue) {
        return booleanField(null, category, key, defaultValue);
    }

    protected ConfigEntry<Boolean> booleanField(String comment, String key, boolean defaultValue) {
        return booleanField(comment, null, key, defaultValue);
    }

    protected ConfigEntry<Boolean> booleanField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, boolean defaultValue) {
        ConfigEntry<Boolean> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.BOOLEAN);

        addComment(comment);
        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<String> stringField(String key, String defaultValue) {
        return stringField(null, null, key, defaultValue);
    }

    protected ConfigEntry<String> stringField(ConfigEntry.Category category, String key, String defaultValue) {
        return stringField(null, category, key, defaultValue);
    }

    protected ConfigEntry<String> stringField(String comment, String key, String defaultValue) {
        return stringField(comment, null, key, defaultValue);
    }

    protected ConfigEntry<String> stringField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, String defaultValue) {
        ConfigEntry<String> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.STRING);

        addComment(comment);
        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<int[]> intArray(String key, int[] defaultValue) {
        return intArray(null, null, key, defaultValue);
    }

    protected ConfigEntry<int[]> intArray(ConfigEntry.Category category, String key, int[] defaultValue) {
        return intArray(null, category, key, defaultValue);
    }

    protected ConfigEntry<int[]> intArray(String comment, String key, int[] defaultValue) {
        return intArray(comment, null, key, defaultValue);
    }

    protected ConfigEntry<int[]> intArray(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, int[] defaultValue) {
        ConfigEntry<int[]> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.INTEGER_ARRAY);

        addComment(comment);
        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<short[]> shortArray(String key, short[] defaultValue) {
        return shortArray(null, null, key, defaultValue);
    }

    protected ConfigEntry<short[]> shortArray(ConfigEntry.Category category, String key, short[] defaultValue) {
        return shortArray(null, category, key, defaultValue);
    }

    protected ConfigEntry<short[]> shortArray(String comment, String key, short[] defaultValue) {
        return shortArray(comment, null, key, defaultValue);
    }

    protected ConfigEntry<short[]> shortArray(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, short[] defaultValue) {
        ConfigEntry<short[]> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.SHORT_ARRAY);

        addComment(comment);
        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<long[]> longArray(String key, long[] defaultValue) {
        return longArray(null, null, key, defaultValue);
    }

    protected ConfigEntry<long[]> longArray(ConfigEntry.Category category, String key, long[] defaultValue) {
        return longArray(null, category, key, defaultValue);
    }

    protected ConfigEntry<long[]> longArray(String comment, String key, long[] defaultValue) {
        return longArray(comment, null, key, defaultValue);
    }

    protected ConfigEntry<long[]> longArray(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, long[] defaultValue) {
        ConfigEntry<long[]> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.LONG_ARRAY);

        addComment(comment);
        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<float[]> floatArray(String key, float[] defaultValue) {
        return floatArray(null, null, key, defaultValue);
    }

    protected ConfigEntry<float[]> floatArray(ConfigEntry.Category category, String key, float[] defaultValue) {
        return floatArray(null, category, key, defaultValue);
    }

    protected ConfigEntry<float[]> floatArray(String comment, String key, float[] defaultValue) {
        return floatArray(comment, null, key, defaultValue);
    }

    protected ConfigEntry<float[]> floatArray(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, float[] defaultValue) {
        ConfigEntry<float[]> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.FLOAT_ARRAY);

        addComment(comment);
        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<double[]> doubleArray(String key, double[] defaultValue) {
        return doubleArray(null, null, key, defaultValue);
    }

    protected ConfigEntry<double[]> doubleArray(ConfigEntry.Category category, String key, double[] defaultValue) {
        return doubleArray(null, category, key, defaultValue);
    }

    protected ConfigEntry<double[]> doubleArray(String comment, String key, double[] defaultValue) {
        return doubleArray(comment, null, key, defaultValue);
    }

    protected ConfigEntry<double[]> doubleArray(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, double[] defaultValue) {
        ConfigEntry<double[]> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.DOUBLE_ARRAY);

        addComment(comment);
        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<boolean[]> booleanArray(String key, boolean[] defaultValue) {
        return booleanArray(null, null, key, defaultValue);
    }

    protected ConfigEntry<boolean[]> booleanArray(ConfigEntry.Category category, String key, boolean[] defaultValue) {
        return booleanArray(null, category, key, defaultValue);
    }

    protected ConfigEntry<boolean[]> booleanArray(String comment, String key, boolean[] defaultValue) {
        return booleanArray(comment, null, key, defaultValue);
    }

    protected ConfigEntry<boolean[]> booleanArray(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, boolean[] defaultValue) {
        ConfigEntry<boolean[]> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.BOOLEAN_ARRAY);

        addComment(comment);
        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<String[]> stringArray(String key, String[] defaultValue) {
        return stringArray(null, null, key, defaultValue);
    }

    protected ConfigEntry<String[]> stringArray(ConfigEntry.Category category, String key, String[] defaultValue) {
        return stringArray(null, category, key, defaultValue);
    }

    protected ConfigEntry<String[]> stringArray(String comment, String key, String[] defaultValue) {
        return stringArray(comment, null, key, defaultValue);
    }

    protected ConfigEntry<String[]> stringArray(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, String[] defaultValue) {
        ConfigEntry<String[]> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.STRING_ARRAY);

        addComment(comment);
        addEntry(entry);

        return entry;
    }

    protected void addEmptyLine() {
        defaultLines.add("");
        lines.add("");
    }

    protected void addComment(String comment) {
        String formattedComment = "# " + comment;

        defaultLines.add(formattedComment);
        lines.add(formattedComment);
    }

    protected void addEntry(ConfigEntry<?> entry) {
        String entryAsString = entry.fullKey() + " = " + entry.getFormattedValue();

        defaultLines.add(entryAsString);
        lines.add(entryAsString);
        entries.put(entry.fullKey(), entry);
    }

    protected abstract void onUpdate();

    public void load() {
        Path configDirectory = FabricLoader.getInstance().getGameDir().resolve("wool").resolve("config");
        Path filePath = configDirectory.resolve(fileName + ".txt");
        Map<String, String> existing = new HashMap<>();

        if (Files.exists(filePath)) {

            try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
                List<String> previousLines = new ArrayList<>();
                String line;

                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    if (line.isBlank() || line.startsWith("#")) continue;

                    int equals = line.indexOf('=');

                    if (equals <= 0) continue;

                    String key = line.substring(0, equals).trim();
                    String value = line.substring(equals + 1).trim();

                    previousLines.add(line);
                    existing.put(key, value);
                }

                this.previousLines = previousLines;
            } catch (IOException exception) {

                if (WoolConfig.developerMode) Wool.LOGGER.error("An error occurred while reading config {}: {}", filePath, exception.getMessage());
            }
        }

        try {
            Files.createDirectories(configDirectory);
        } catch (IOException exception) {

            if (WoolConfig.developerMode) Wool.LOGGER.error("Can't to create config directory {}: {}", configDirectory, exception.getMessage());
        }

        List<String> outputLines = new ArrayList<>();

        for (String line : lines) {

            if (line.isBlank() || line.startsWith("#")) {
                outputLines.add(line);

                continue;
            }

            String[] split = line.split("=", 2);

            if (split.length != 2) continue;

            String key = split[0].trim();
            ConfigEntry<?> entry = entries.get(key);

            if (entries.containsKey(key) && existing.containsKey(key)) {
                entry.parseAndSetValue(existing.get(key));
                outputLines.add(key + " = " + existing.get(key));
            } else {
                outputLines.add(key + " = " + entries.get(key).getFormattedValue());
            }
        }

        onUpdate();

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {

            for (String line : outputLines) {
                writer.write(line);
                writer.newLine();
            }

            lines = outputLines;
        } catch (IOException exception) {

            if (WoolConfig.developerMode) Wool.LOGGER.error("An error occurred while writing config {}: {}", filePath, exception.getMessage());
        }
    }

    public void save() {
        Path configDirectory = FabricLoader.getInstance().getGameDir().resolve("wool").resolve("config");
        Path filePath = configDirectory.resolve(fileName);

        if (Files.exists(filePath)) {

            try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
                List<String> previousLines = new ArrayList<>();
                String line;

                while ((line = reader.readLine()) != null) {
                    previousLines.add(line);
                }

                this.previousLines = previousLines;
            } catch (IOException exception) {

                if (WoolConfig.developerMode) Wool.LOGGER.error("An error occurred while reading config {}: {}", filePath, exception.getMessage());
            }
        }

        try {
            Files.createDirectories(configDirectory);
        } catch (IOException exception) {

            if (WoolConfig.developerMode) Wool.LOGGER.error("Can't create config directory {}: {}", configDirectory, exception.getMessage());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {

            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException exception) {

            if (WoolConfig.developerMode) Wool.LOGGER.error("An error occurred while writing config {}: {}", filePath, exception.getMessage());
        }
    }

    public void reset() {
        Path configDirectory = FabricLoader.getInstance().getGameDir().resolve("wool").resolve("config");
        Path filePath = configDirectory.resolve(fileName + ".txt");

        try {
            Files.createDirectories(configDirectory);
        } catch (IOException exception) {

            if (WoolConfig.developerMode) Wool.LOGGER.error("Can't create config directory {}: {}", configDirectory, exception.getMessage());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            List<String> previousLines = this.previousLines != null ? this.previousLines : defaultLines;

            for (String line : previousLines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException exception) {

            if (WoolConfig.developerMode) Wool.LOGGER.error("An error occurred while writing config {}: {}", filePath, exception.getMessage());
        }
    }

    public void resetToDefault() {
        Path configDirectory = FabricLoader.getInstance().getGameDir().resolve("wool").resolve("config");
        Path filePath = configDirectory.resolve(fileName + ".txt");

        try {
            Files.createDirectories(configDirectory);
        } catch (IOException exception) {

            if (WoolConfig.developerMode) Wool.LOGGER.error("Can't create config directory {}: {}", configDirectory, exception.getMessage());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {

            for (String line : defaultLines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException exception) {

            if (WoolConfig.developerMode) Wool.LOGGER.error("An error occurred while writing config {}: {}", filePath, exception.getMessage());
        }
    }
}
