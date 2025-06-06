package net.mrwooly357.wool.config;

import net.mrwooly357.wool.config.field_type.FieldType;
import org.jetbrains.annotations.Nullable;

public class ConfigEntry<T> {

    private final @Nullable Category category;
    private final String key;
    private T value;
    private final FieldType<T> type;

    public ConfigEntry(@Nullable Category category, String key, T defaultValue, FieldType<T> type) {
        this.category = category;
        this.key = key;
        this.value = defaultValue;
        this.type = type;
    }


    public String fullKey() {
        return category == null ? key : category.name() + "." + key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getFormattedValue() {
        return type.format(value);
    }

    public void parseAndSetValue(String string) {
        this.value = type.parse(string);
    }


    public record Category(String name) {}
}
