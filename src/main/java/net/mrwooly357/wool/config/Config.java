package net.mrwooly357.wool.config;

import net.fabricmc.loader.api.FabricLoader;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.config.field_type.FieldType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Config {

    private final List<ConfigEntry<?>> entries = new ArrayList<>();
    private final String fileName;

    public Config(String fileName) {
        this.fileName = fileName;
    }


    public ConfigEntry<Integer> intField(String key, int defaultValue) {
        return intField(null, null, key, defaultValue);
    }

    public ConfigEntry<Integer> intField(ConfigEntry.Category category, String key, int defaultValue) {
        return intField(null, category, key, defaultValue);
    }

    public ConfigEntry<Integer> intField(String comment, String key, int defaultValue) {
        return intField(comment, null, key, defaultValue);
    }

    public ConfigEntry<Integer> intField(String comment, ConfigEntry.Category category, String key, int defaultValue) {
        ConfigEntry<Integer> entry =  new ConfigEntry<>(comment, category, key, defaultValue, FieldType.INTEGER);

        entries.add(entry);

        return entry;
    }

    public ConfigEntry<Short> shortField(String key, short defaultValue) {
        return shortField(null, null, key, defaultValue);
    }

    public ConfigEntry<Short> shortField(ConfigEntry.Category category, String key, short defaultValue) {
        return shortField(null, category, key, defaultValue);
    }

    public ConfigEntry<Short> shortField(String comment, String key, short defaultValue) {
        return shortField(comment, null, key, defaultValue);
    }

    public ConfigEntry<Short> shortField(String comment, ConfigEntry.Category category, String key, short defaultValue) {
        ConfigEntry<Short> entry =  new ConfigEntry<>(comment, category, key, defaultValue, FieldType.SHORT);

        entries.add(entry);

        return entry;
    }

    public ConfigEntry<Long> longField(String key, long defaultValue) {
        return longField(null, null, key, defaultValue);
    }

    public ConfigEntry<Long> longField(ConfigEntry.Category category, String key, long defaultValue) {
        return longField(null, category, key, defaultValue);
    }

    public ConfigEntry<Long> longField(String comment, String key, long defaultValue) {
        return longField(comment, null, key, defaultValue);
    }

    public ConfigEntry<Long> longField(String comment, ConfigEntry.Category category, String key, long defaultValue) {
        ConfigEntry<Long> entry =  new ConfigEntry<>(comment, category, key, defaultValue, FieldType.LONG);

        entries.add(entry);

        return entry;
    }

    public ConfigEntry<Float> floatField(String key, float defaultValue) {
        return floatField(null, null, key, defaultValue);
    }

    public ConfigEntry<Float> floatField(ConfigEntry.Category category, String key, float defaultValue) {
        return floatField(null, category, key, defaultValue);
    }

    public ConfigEntry<Float> floatField(String comment, String key, float defaultValue) {
        return floatField(comment, null, key, defaultValue);
    }

    public ConfigEntry<Float> floatField(String comment, ConfigEntry.Category category, String key, float defaultValue) {
        ConfigEntry<Float> entry =  new ConfigEntry<>(comment, category, key, defaultValue, FieldType.FLOAT);

        entries.add(entry);

        return entry;
    }

    public ConfigEntry<Double> doubleField(String key, double defaultValue) {
        return doubleField(null, null, key, defaultValue);
    }

    public ConfigEntry<Double> doubleField(ConfigEntry.Category category, String key, double defaultValue) {
        return doubleField(null, category, key, defaultValue);
    }

    public ConfigEntry<Double> doubleField(String comment, String key, double defaultValue) {
        return doubleField(comment, null, key, defaultValue);
    }

    public ConfigEntry<Double> doubleField(String comment, ConfigEntry.Category category, String key, double defaultValue) {
        ConfigEntry<Double> entry =  new ConfigEntry<>(comment, category, key, defaultValue, FieldType.DOUBLE);

        entries.add(entry);

        return entry;
    }

    public ConfigEntry<Boolean> booleanField(String key, boolean defaultValue) {
        return booleanField(null, null, key, defaultValue);
    }

    public ConfigEntry<Boolean> booleanField(ConfigEntry.Category category, String key, boolean defaultValue) {
        return booleanField(null, category, key, defaultValue);
    }

    public ConfigEntry<Boolean> booleanField(String comment, String key, boolean defaultValue) {
        return booleanField(comment, null, key, defaultValue);
    }

    public ConfigEntry<Boolean> booleanField(String comment, ConfigEntry.Category category, String key, boolean defaultValue) {
        ConfigEntry<Boolean> entry =  new ConfigEntry<>(comment, category, key, defaultValue, FieldType.BOOLEAN);

        entries.add(entry);

        return entry;
    }

    public ConfigEntry<String> stringField(String key, String defaultValue) {
        return stringField(null, null, key, defaultValue);
    }

    public ConfigEntry<String> stringField(ConfigEntry.Category category, String key, String defaultValue) {
        return stringField(null, category, key, defaultValue);
    }

    public ConfigEntry<String> stringField(String comment, String key, String defaultValue) {
        return stringField(comment, null, key, defaultValue);
    }

    public ConfigEntry<String> stringField(String comment, ConfigEntry.Category category, String key, String defaultValue) {
        ConfigEntry<String> entry =  new ConfigEntry<>(comment, category, key, defaultValue, FieldType.STRING);

        entries.add(entry);

        return entry;
    }

    public ConfigEntry<int[]> intArray(String key, int[] defaultValue) {
        return intArray(null, null, key, defaultValue);
    }

    public ConfigEntry<int[]> intArray(ConfigEntry.Category category, String key, int[] defaultValue) {
        return intArray(null, category, key, defaultValue);
    }

    public ConfigEntry<int[]> intArray(String comment, String key, int[] defaultValue) {
        return intArray(comment, null, key, defaultValue);
    }

    public ConfigEntry<int[]> intArray(String comment, ConfigEntry.Category category, String key, int[] defaultValue) {
        ConfigEntry<int[]> entry =  new ConfigEntry<>(comment, category, key, defaultValue, FieldType.INTEGER_ARRAY);

        entries.add(entry);

        return entry;
    }

    public ConfigEntry<short[]> shortArray(String key, short[] defaultValue) {
        return shortArray(null, null, key, defaultValue);
    }

    public ConfigEntry<short[]> shortArray(ConfigEntry.Category category, String key, short[] defaultValue) {
        return shortArray(null, category, key, defaultValue);
    }

    public ConfigEntry<short[]> shortArray(String comment, String key, short[] defaultValue) {
        return shortArray(comment, null, key, defaultValue);
    }

    public ConfigEntry<short[]> shortArray(String comment, ConfigEntry.Category category, String key, short[] defaultValue) {
        ConfigEntry<short[]> entry =  new ConfigEntry<>(comment, category, key, defaultValue, FieldType.SHORT_ARRAY);

        entries.add(entry);

        return entry;
    }

    public ConfigEntry<long[]> longArray(String key, long[] defaultValue) {
        return longArray(null, null, key, defaultValue);
    }

    public ConfigEntry<long[]> longArray(ConfigEntry.Category category, String key, long[] defaultValue) {
        return longArray(null, category, key, defaultValue);
    }

    public ConfigEntry<long[]> longArray(String comment, String key, long[] defaultValue) {
        return longArray(comment, null, key, defaultValue);
    }

    public ConfigEntry<long[]> longArray(String comment, ConfigEntry.Category category, String key, long[] defaultValue) {
        ConfigEntry<long[]> entry =  new ConfigEntry<>(comment, category, key, defaultValue, FieldType.LONG_ARRAY);

        entries.add(entry);

        return entry;
    }

    public ConfigEntry<float[]> floatArray(String key, float[] defaultValue) {
        return floatArray(null, null, key, defaultValue);
    }

    public ConfigEntry<float[]> floatArray(ConfigEntry.Category category, String key, float[] defaultValue) {
        return floatArray(null, category, key, defaultValue);
    }

    public ConfigEntry<float[]> floatArray(String comment, String key, float[] defaultValue) {
        return floatArray(comment, null, key, defaultValue);
    }

    public ConfigEntry<float[]> floatArray(String comment, ConfigEntry.Category category, String key, float[] defaultValue) {
        ConfigEntry<float[]> entry =  new ConfigEntry<>(comment, category, key, defaultValue, FieldType.FLOAT_ARRAY);

        entries.add(entry);

        return entry;
    }

    public ConfigEntry<double[]> doubleArray(String key, double[] defaultValue) {
        return doubleArray(null, null, key, defaultValue);
    }

    public ConfigEntry<double[]> doubleArray(ConfigEntry.Category category, String key, double[] defaultValue) {
        return doubleArray(null, category, key, defaultValue);
    }

    public ConfigEntry<double[]> doubleArray(String comment, String key, double[] defaultValue) {
        return doubleArray(comment, null, key, defaultValue);
    }

    public ConfigEntry<double[]> doubleArray(String comment, ConfigEntry.Category category, String key, double[] defaultValue) {
        ConfigEntry<double[]> entry =  new ConfigEntry<>(comment, category, key, defaultValue, FieldType.DOUBLE_ARRAY);

        entries.add(entry);

        return entry;
    }

    public ConfigEntry<boolean[]> booleanArray(String key, boolean[] defaultValue) {
        return booleanArray(null, null, key, defaultValue);
    }

    public ConfigEntry<boolean[]> booleanArray(ConfigEntry.Category category, String key, boolean[] defaultValue) {
        return booleanArray(null, category, key, defaultValue);
    }

    public ConfigEntry<boolean[]> booleanArray(String comment, String key, boolean[] defaultValue) {
        return booleanArray(comment, null, key, defaultValue);
    }

    public ConfigEntry<boolean[]> booleanArray(String comment, ConfigEntry.Category category, String key, boolean[] defaultValue) {
        ConfigEntry<boolean[]> entry =  new ConfigEntry<>(comment, category, key, defaultValue, FieldType.BOOLEAN_ARRAY);

        entries.add(entry);

        return entry;
    }

    public ConfigEntry<String[]> stringArray(String key, String[] defaultValue) {
        return stringArray(null, null, key, defaultValue);
    }

    public ConfigEntry<String[]> stringArray(ConfigEntry.Category category, String key, String[] defaultValue) {
        return stringArray(null, category, key, defaultValue);
    }

    public ConfigEntry<String[]> stringArray(String comment, String key, String[] defaultValue) {
        return stringArray(comment, null, key, defaultValue);
    }

    public ConfigEntry<String[]> stringArray(String comment, ConfigEntry.Category category, String key, String[] defaultValue) {
        ConfigEntry<String[]> entry =  new ConfigEntry<>(comment, category, key, defaultValue, FieldType.STRING_ARRAY);

        entries.add(entry);

        return entry;
    }

    public abstract void onUpdate();

    public void reload() {
        Path configDirectory = FabricLoader.getInstance().getGameDir().resolve("wool").resolve("config");
        Path filePath = configDirectory.resolve(fileName + ".txt");

        Map<String, String> existing = new HashMap<>();

        if (Files.exists(filePath)) {

            try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
                String line;

                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    if (line.isEmpty() || line.startsWith("#")) continue;

                    int equals = line.indexOf('=');

                    if (equals <= 0) continue;

                    String key = line.substring(0, equals).trim();
                    String value = line.substring(equals + 1).trim();

                    existing.put(key, value);
                }
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

        for (ConfigEntry<?> entry : entries) {
            String comment = entry.getComment();
            String fullKey = entry.fullKey();
            String valueString = existing.getOrDefault(fullKey, entry.formatValue());

            if (comment != null && !comment.isEmpty()) {
                outputLines.add("# " + comment);
            }

            outputLines.add(fullKey + " = " + valueString);
            entry.parseValue(valueString);
            onUpdate();
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {

            for (String line : outputLines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException exception) {

            if (WoolConfig.developerMode) Wool.LOGGER.error("An error occurred while writing config {}: {}", filePath, exception.getMessage());
        }
    }

    public void save() {
        Path configDirectory = FabricLoader.getInstance().getGameDir().resolve("wool").resolve("config");
        Path filePath = configDirectory.resolve(fileName);

        try {
            Files.createDirectories(configDirectory);
        } catch (IOException exception) {

            if (WoolConfig.developerMode) Wool.LOGGER.error("Can't create config directory {}: {}", configDirectory, exception.getMessage());
        }

        List<String> outputLines = new ArrayList<>();

        for (ConfigEntry<?> entry : entries) {
            String comment = entry.getComment();

            if (comment != null && !comment.isEmpty()) {
                outputLines.add("# " + comment);
            }

            outputLines.add(entry.fullKey() + " = " + entry.formatValue());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {

            for (String line : outputLines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException exception) {

            if (WoolConfig.developerMode) Wool.LOGGER.error("An error occurred while writing config {}: {}", filePath, exception.getMessage());
        }
    }
}
