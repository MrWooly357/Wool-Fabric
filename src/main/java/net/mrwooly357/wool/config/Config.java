package net.mrwooly357.wool.config;

import net.fabricmc.loader.api.FabricLoader;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.config.field_type.FieldType;
import net.mrwooly357.wool.config.restriction.Restriction;
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


    protected ConfigEntry<Byte> byteField(String key, byte defaultValue) {

        return byteField(key, defaultValue, null);
    }

    protected ConfigEntry<Byte> byteField(ConfigEntry.Category category, String key, byte defaultValue) {

        return byteField(category, key, defaultValue, null);
    }

    protected ConfigEntry<Byte> byteField(String comment, String key, byte defaultValue) {

        return byteField(comment, key, defaultValue, null);
    }

    protected ConfigEntry<Byte> byteField(String key, byte defaultValue, Restriction<Byte> restriction) {

        return byteField(null, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<Byte> byteField(ConfigEntry.Category category, String key, byte defaultValue, Restriction<Byte> restriction) {

        return byteField(null, category, key, defaultValue, restriction);
    }

    protected ConfigEntry<Byte> byteField(String comment, String key, byte defaultValue, Restriction<Byte> restriction) {

        return byteField(comment, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<Byte> byteField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, byte defaultValue, @Nullable Restriction<Byte> restriction) {

        ConfigEntry<Byte> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.BYTE, restriction);

        if (comment != null) addComment(comment);

        if (restriction != null) addComment(restriction.getComment());

        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<Short> shortField(String key, short defaultValue) {

        return shortField(key, defaultValue, null);
    }

    protected ConfigEntry<Short> shortField(ConfigEntry.Category category, String key, short defaultValue) {

        return shortField(category, key, defaultValue, null);
    }

    protected ConfigEntry<Short> shortField(String comment, String key, short defaultValue) {

        return shortField(comment, key, defaultValue, null);
    }

    protected ConfigEntry<Short> shortField(String key, short defaultValue, Restriction<Short> restriction) {

        return shortField(null, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<Short> shortField(ConfigEntry.Category category, String key, short defaultValue, Restriction<Short> restriction) {

        return shortField(null, category, key, defaultValue, restriction);
    }

    protected ConfigEntry<Short> shortField(String comment, String key, short defaultValue, Restriction<Short> restriction) {

        return shortField(comment, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<Short> shortField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, short defaultValue, @Nullable Restriction<Short> restriction) {

        ConfigEntry<Short> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.SHORT, restriction);

        if (comment != null) addComment(comment);

        if (restriction != null) addComment(restriction.getComment());

        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<Integer> intField(String key, int defaultValue) {

        return intField(key, defaultValue, null);
    }

    protected ConfigEntry<Integer> intField(ConfigEntry.Category category, String key, int defaultValue) {

        return intField(category, key, defaultValue, null);
    }

    protected ConfigEntry<Integer> intField(String comment, String key, int defaultValue) {

        return intField(comment, key, defaultValue, null);
    }

    protected ConfigEntry<Integer> intField(String key, int defaultValue, Restriction<Integer> restriction) {

        return intField(null, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<Integer> intField(ConfigEntry.Category category, String key, int defaultValue, Restriction<Integer> restriction) {

        return intField(null, category, key, defaultValue, restriction);
    }

    protected ConfigEntry<Integer> intField(String comment, String key, int defaultValue, Restriction<Integer> restriction) {

        return intField(comment, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<Integer> intField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, int defaultValue, @Nullable Restriction<Integer> restriction) {

        ConfigEntry<Integer> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.INT, restriction);

        if (comment != null) addComment(comment);

        if (restriction != null) addComment(restriction.getComment());

        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<Long> longField(String key, long defaultValue) {

        return longField(key, defaultValue, null);
    }

    protected ConfigEntry<Long> longField(ConfigEntry.Category category, String key, long defaultValue) {

        return longField(category, key, defaultValue, null);
    }

    protected ConfigEntry<Long> longField(String comment, String key, long defaultValue) {

        return longField(comment, key, defaultValue, null);
    }

    protected ConfigEntry<Long> longField(String key, long defaultValue, Restriction<Long> restriction) {

        return longField(null, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<Long> longField(ConfigEntry.Category category, String key, long defaultValue, Restriction<Long> restriction) {

        return longField(null, category, key, defaultValue, restriction);
    }

    protected ConfigEntry<Long> longField(String comment, String key, long defaultValue, Restriction<Long> restriction) {

        return longField(comment, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<Long> longField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, long defaultValue, @Nullable Restriction<Long> restriction) {

        ConfigEntry<Long> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.LONG, restriction);

        if (comment != null) addComment(comment);

        if (restriction != null) addComment(restriction.getComment());

        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<Float> floatField(String key, float defaultValue) {

        return floatField(key, defaultValue, null);
    }

    protected ConfigEntry<Float> floatField(ConfigEntry.Category category, String key, float defaultValue) {

        return floatField(category, key, defaultValue, null);
    }

    protected ConfigEntry<Float> floatField(String comment, String key, float defaultValue) {

        return floatField(comment, key, defaultValue, null);
    }

    protected ConfigEntry<Float> floatField(String key, float defaultValue, Restriction<Float> restriction) {

        return floatField(null, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<Float> floatField(ConfigEntry.Category category, String key, float defaultValue, Restriction<Float> restriction) {

        return floatField(null, category, key, defaultValue, restriction);
    }

    protected ConfigEntry<Float> floatField(String comment, String key, float defaultValue, Restriction<Float> restriction) {

        return floatField(comment, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<Float> floatField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, float defaultValue, @Nullable Restriction<Float> restriction) {

        ConfigEntry<Float> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.FLOAT, restriction);

        if (comment != null) addComment(comment);

        if (restriction != null) addComment(restriction.getComment());

        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<Double> doubleField(String key, double defaultValue) {

        return doubleField(key, defaultValue, null);
    }

    protected ConfigEntry<Double> doubleField(ConfigEntry.Category category, String key, double defaultValue) {

        return doubleField(category, key, defaultValue, null);
    }

    protected ConfigEntry<Double> doubleField(String comment, String key, double defaultValue) {

        return doubleField(comment, key, defaultValue, null);
    }

    protected ConfigEntry<Double> doubleField(String key, double defaultValue, Restriction<Double> restriction) {

        return doubleField(null, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<Double> doubleField(ConfigEntry.Category category, String key, double defaultValue, Restriction<Double> restriction) {

        return doubleField(null, category, key, defaultValue, restriction);
    }

    protected ConfigEntry<Double> doubleField(String comment, String key, double defaultValue, Restriction<Double> restriction) {

        return doubleField(comment, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<Double> doubleField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, double defaultValue, @Nullable Restriction<Double> restriction) {

        ConfigEntry<Double> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.DOUBLE, restriction);

        if (comment != null) addComment(comment);

        if (restriction != null) addComment(restriction.getComment());

        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<Boolean> booleanField(String key, boolean defaultValue) {

        return booleanField(key, defaultValue, null);
    }

    protected ConfigEntry<Boolean> booleanField(ConfigEntry.Category category, String key, boolean defaultValue) {

        return booleanField(category, key, defaultValue, null);
    }

    protected ConfigEntry<Boolean> booleanField(String comment, String key, boolean defaultValue) {

        return booleanField(comment, key, defaultValue, null);
    }

    protected ConfigEntry<Boolean> booleanField(String key, boolean defaultValue, Restriction<Boolean> restriction) {

        return booleanField(null, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<Boolean> booleanField(ConfigEntry.Category category, String key, boolean defaultValue, Restriction<Boolean> restriction) {

        return booleanField(null, category, key, defaultValue, restriction);
    }

    protected ConfigEntry<Boolean> booleanField(String comment, String key, boolean defaultValue, Restriction<Boolean> restriction) {

        return booleanField(comment, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<Boolean> booleanField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, boolean defaultValue, @Nullable Restriction<Boolean> restriction) {

        ConfigEntry<Boolean> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.BOOLEAN, restriction);

        if (comment != null) addComment(comment);

        if (restriction != null) addComment(restriction.getComment());

        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<Character> charField(String key, char defaultValue) {

        return charField(key, defaultValue, null);
    }

    protected ConfigEntry<Character> charField(ConfigEntry.Category category, String key, char defaultValue) {

        return charField(category, key, defaultValue, null);
    }

    protected ConfigEntry<Character> charField(String comment, String key, char defaultValue) {

        return charField(comment, key, defaultValue, null);
    }

    protected ConfigEntry<Character> charField(String key, char defaultValue, Restriction<Character> restriction) {

        return charField(null, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<Character> charField(ConfigEntry.Category category, String key, char defaultValue, Restriction<Character> restriction) {

        return charField(null, category, key, defaultValue, restriction);
    }

    protected ConfigEntry<Character> charField(String comment, String key, char defaultValue, Restriction<Character> restriction) {

        return charField(comment, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<Character> charField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, char defaultValue, @Nullable Restriction<Character> restriction) {

        ConfigEntry<Character> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.CHAR, restriction);

        if (comment != null) addComment(comment);

        if (restriction != null) addComment(restriction.getComment());

        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<String> stringField(String key, String defaultValue) {

        return stringField(key, defaultValue, (Restriction<String>) null);
    }

    protected ConfigEntry<String> stringField(ConfigEntry.Category category, String key, String defaultValue) {

        return stringField(category, key, defaultValue, null);
    }

    protected ConfigEntry<String> stringField(String comment, String key, String defaultValue) {

        return stringField(comment, key, defaultValue, null);
    }

    protected ConfigEntry<String> stringField(String key, String defaultValue, Restriction<String> restriction) {

        return stringField(null, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<String> stringField(ConfigEntry.Category category, String key, String defaultValue, Restriction<String> restriction) {

        return stringField(null, category, key, defaultValue, restriction);
    }

    protected ConfigEntry<String> stringField(String comment, String key, String defaultValue, Restriction<String> restriction) {

        return stringField(comment, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<String> stringField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, String defaultValue, @Nullable Restriction<String> restriction) {

        ConfigEntry<String> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.STRING, restriction);

        if (comment != null) addComment(comment);

        if (restriction != null) addComment(restriction.getComment());

        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<byte[]> byteArrayField(String key, byte[] defaultValue) {

        return byteArrayField(key, defaultValue, null);
    }

    protected ConfigEntry<byte[]> byteArrayField(ConfigEntry.Category category, String key, byte[] defaultValue) {

        return byteArrayField(category, key, defaultValue, null);
    }

    protected ConfigEntry<byte[]> byteArrayField(String comment, String key, byte[] defaultValue) {

        return byteArrayField(comment, key, defaultValue, null);
    }

    protected ConfigEntry<byte[]> byteArrayField(String key, byte[] defaultValue, Restriction<byte[]> restriction) {

        return byteArrayField(null, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<byte[]> byteArrayField(ConfigEntry.Category category, String key, byte[] defaultValue, Restriction<byte[]> restriction) {

        return byteArrayField(null, category, key, defaultValue, restriction);
    }

    protected ConfigEntry<byte[]> byteArrayField(String comment, String key, byte[] defaultValue, Restriction<byte[]> restriction) {

        return byteArrayField(comment, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<byte[]> byteArrayField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, byte[] defaultValue, @Nullable Restriction<byte[]> restriction) {

        ConfigEntry<byte[]> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.BYTE_ARRAY, restriction);

        if (comment != null) addComment(comment);

        if (restriction != null) addComment(restriction.getComment());

        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<short[]> shortArrayField(String key, short[] defaultValue) {

        return shortArrayField(key, defaultValue, null);
    }

    protected ConfigEntry<short[]> shortArrayField(ConfigEntry.Category category, String key, short[] defaultValue) {

        return shortArrayField(category, key, defaultValue, null);
    }

    protected ConfigEntry<short[]> shortArrayField(String comment, String key, short[] defaultValue) {

        return shortArrayField(comment, key, defaultValue, null);
    }

    protected ConfigEntry<short[]> shortArrayField(String key, short[] defaultValue, Restriction<short[]> restriction) {

        return shortArrayField(null, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<short[]> shortArrayField(ConfigEntry.Category category, String key, short[] defaultValue, Restriction<short[]> restriction) {

        return shortArrayField(null, category, key, defaultValue, restriction);
    }

    protected ConfigEntry<short[]> shortArrayField(String comment, String key, short[] defaultValue, Restriction<short[]> restriction) {

        return shortArrayField(comment, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<short[]> shortArrayField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, short[] defaultValue, @Nullable Restriction<short[]> restriction) {

        ConfigEntry<short[]> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.SHORT_ARRAY, restriction);

        if (comment != null) addComment(comment);

        if (restriction != null) addComment(restriction.getComment());

        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<int[]> intArrayField(String key, int[] defaultValue) {

        return intArrayField(key, defaultValue, null);
    }

    protected ConfigEntry<int[]> intArrayField(ConfigEntry.Category category, String key, int[] defaultValue) {

        return intArrayField(category, key, defaultValue, null);
    }

    protected ConfigEntry<int[]> intArrayField(String comment, String key, int[] defaultValue) {

        return intArrayField(comment, key, defaultValue, null);
    }

    protected ConfigEntry<int[]> intArrayField(String key, int[] defaultValue, Restriction<int[]> restriction) {

        return intArrayField(null, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<int[]> intArrayField(ConfigEntry.Category category, String key, int[] defaultValue, Restriction<int[]> restriction) {

        return intArrayField(null, category, key, defaultValue, restriction);
    }

    protected ConfigEntry<int[]> intArrayField(String comment, String key, int[] defaultValue, Restriction<int[]> restriction) {

        return intArrayField(comment, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<int[]> intArrayField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, int[] defaultValue, @Nullable Restriction<int[]> restriction) {

        ConfigEntry<int[]> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.INT_ARRAY, restriction);

        if (comment != null) addComment(comment);

        if (restriction != null) addComment(restriction.getComment());

        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<long[]> longArrayField(String key, long[] defaultValue) {

        return longArrayField(key, defaultValue, null);
    }

    protected ConfigEntry<long[]> longArrayField(ConfigEntry.Category category, String key, long[] defaultValue) {

        return longArrayField(category, key, defaultValue, null);
    }

    protected ConfigEntry<long[]> longArrayField(String comment, String key, long[] defaultValue) {

        return longArrayField(comment, key, defaultValue, null);
    }

    protected ConfigEntry<long[]> longArrayField(String key, long[] defaultValue, Restriction<long[]> restriction) {

        return longArrayField(null, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<long[]> longArrayField(ConfigEntry.Category category, String key, long[] defaultValue, Restriction<long[]> restriction) {

        return longArrayField(null, category, key, defaultValue, restriction);
    }

    protected ConfigEntry<long[]> longArrayField(String comment, String key, long[] defaultValue, Restriction<long[]> restriction) {

        return longArrayField(comment, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<long[]> longArrayField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, long[] defaultValue, @Nullable Restriction<long[]> restriction) {

        ConfigEntry<long[]> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.LONG_ARRAY, restriction);

        if (comment != null) addComment(comment);

        if (restriction != null) addComment(restriction.getComment());

        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<float[]> floatArrayField(String key, float[] defaultValue) {

        return floatArrayField(key, defaultValue, null);
    }

    protected ConfigEntry<float[]> floatArrayField(ConfigEntry.Category category, String key, float[] defaultValue) {

        return floatArrayField(category, key, defaultValue, null);
    }

    protected ConfigEntry<float[]> floatArrayField(String comment, String key, float[] defaultValue) {

        return floatArrayField(comment, key, defaultValue, null);
    }

    protected ConfigEntry<float[]> floatArrayField(String key, float[] defaultValue, Restriction<float[]> restriction) {

        return floatArrayField(null, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<float[]> floatArrayField(ConfigEntry.Category category, String key, float[] defaultValue, Restriction<float[]> restriction) {

        return floatArrayField(null, category, key, defaultValue, restriction);
    }

    protected ConfigEntry<float[]> floatArrayField(String comment, String key, float[] defaultValue, Restriction<float[]> restriction) {

        return floatArrayField(comment, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<float[]> floatArrayField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, float[] defaultValue, @Nullable Restriction<float[]> restriction) {

        ConfigEntry<float[]> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.FLOAT_ARRAY, restriction);

        if (comment != null) addComment(comment);

        if (restriction != null) addComment(restriction.getComment());

        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<double[]> doubleArrayField(String key, double[] defaultValue) {

        return doubleArrayField(key, defaultValue, null);
    }

    protected ConfigEntry<double[]> doubleArrayField(ConfigEntry.Category category, String key, double[] defaultValue) {

        return doubleArrayField(category, key, defaultValue, null);
    }

    protected ConfigEntry<double[]> doubleArrayField(String comment, String key, double[] defaultValue) {

        return doubleArrayField(comment, key, defaultValue, null);
    }

    protected ConfigEntry<double[]> doubleArrayField(String key, double[] defaultValue, Restriction<double[]> restriction) {

        return doubleArrayField(null, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<double[]> doubleArrayField(ConfigEntry.Category category, String key, double[] defaultValue, Restriction<double[]> restriction) {

        return doubleArrayField(null, category, key, defaultValue, restriction);
    }

    protected ConfigEntry<double[]> doubleArrayField(String comment, String key, double[] defaultValue, Restriction<double[]> restriction) {

        return doubleArrayField(comment, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<double[]> doubleArrayField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, double[] defaultValue, @Nullable Restriction<double[]> restriction) {

        ConfigEntry<double[]> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.DOUBLE_ARRAY, restriction);

        if (comment != null) addComment(comment);

        if (restriction != null) addComment(restriction.getComment());

        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<boolean[]> booleanArrayField(String key, boolean[] defaultValue) {

        return booleanArrayField(key, defaultValue, null);
    }

    protected ConfigEntry<boolean[]> booleanArrayField(ConfigEntry.Category category, String key, boolean[] defaultValue) {

        return booleanArrayField(category, key, defaultValue, null);
    }

    protected ConfigEntry<boolean[]> booleanArrayField(String comment, String key, boolean[] defaultValue) {

        return booleanArrayField(comment, key, defaultValue, null);
    }

    protected ConfigEntry<boolean[]> booleanArrayField(String key, boolean[] defaultValue, Restriction<boolean[]> restriction) {

        return booleanArrayField(null, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<boolean[]> booleanArrayField(ConfigEntry.Category category, String key, boolean[] defaultValue, Restriction<boolean[]> restriction) {

        return booleanArrayField(null, category, key, defaultValue, restriction);
    }

    protected ConfigEntry<boolean[]> booleanArrayField(String comment, String key, boolean[] defaultValue, Restriction<boolean[]> restriction) {

        return booleanArrayField(comment, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<boolean[]> booleanArrayField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, boolean[] defaultValue, @Nullable Restriction<boolean[]> restriction) {

        ConfigEntry<boolean[]> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.BOOLEAN_ARRAY, restriction);

        if (comment != null) addComment(comment);

        if (restriction != null) addComment(restriction.getComment());

        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<char[]> charArrayField(String key, char[] defaultValue) {

        return charArrayField(key, defaultValue, null);
    }

    protected ConfigEntry<char[]> charArrayField(ConfigEntry.Category category, String key, char[] defaultValue) {

        return charArrayField(category, key, defaultValue, null);
    }

    protected ConfigEntry<char[]> charArrayField(String comment, String key, char[] defaultValue) {

        return charArrayField(comment, key, defaultValue, null);
    }

    protected ConfigEntry<char[]> charArrayField(String key, char[] defaultValue, Restriction<char[]> restriction) {

        return charArrayField(null, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<char[]> charArrayField(ConfigEntry.Category category, String key, char[] defaultValue, Restriction<char[]> restriction) {

        return charArrayField(null, category, key, defaultValue, restriction);
    }

    protected ConfigEntry<char[]> charArrayField(String comment, String key, char[] defaultValue, Restriction<char[]> restriction) {

        return charArrayField(comment, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<char[]> charArrayField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, char[] defaultValue, @Nullable Restriction<char[]> restriction) {

        ConfigEntry<char[]> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.CHAR_ARRAY, restriction);

        if (comment != null) addComment(comment);

        if (restriction != null) addComment(restriction.getComment());

        addEntry(entry);

        return entry;
    }

    protected ConfigEntry<String[]> stringArrayField(String key, String[] defaultValue) {

        return stringArrayField(key, defaultValue, null);
    }

    protected ConfigEntry<String[]> stringArrayField(ConfigEntry.Category category, String key, String[] defaultValue) {

        return stringArrayField(category, key, defaultValue, null);
    }

    protected ConfigEntry<String[]> stringArrayField(String comment, String key, String[] defaultValue) {

        return stringArrayField(comment, key, defaultValue, null);
    }

    protected ConfigEntry<String[]> stringArrayField(String key, String[] defaultValue, Restriction<String[]> restriction) {

        return stringArrayField(null, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<String[]> stringArrayField(ConfigEntry.Category category, String key, String[] defaultValue, Restriction<String[]> restriction) {

        return stringArrayField(null, category, key, defaultValue, restriction);
    }

    protected ConfigEntry<String[]> stringArrayField(String comment, String key, String[] defaultValue, Restriction<String[]> restriction) {

        return stringArrayField(comment, null, key, defaultValue, restriction);
    }

    protected ConfigEntry<String[]> stringArrayField(@Nullable String comment, @Nullable ConfigEntry.Category category, String key, String[] defaultValue, @Nullable Restriction<String[]> restriction) {

        ConfigEntry<String[]> entry =  new ConfigEntry<>(category, key, defaultValue, FieldType.STRING_ARRAY, restriction);

        if (comment != null) addComment(comment);

        if (restriction != null) addComment(restriction.getComment());

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
