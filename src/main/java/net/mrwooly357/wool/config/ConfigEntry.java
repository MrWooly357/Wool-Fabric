package net.mrwooly357.wool.config;

import net.mrwooly357.wool.config.field_type.FieldType;
import org.jetbrains.annotations.Nullable;

public class ConfigEntry<T> {

    private final @Nullable String comment;
    private final @Nullable Category category;
    private final String key;
    private final T defaultValue;
    private T value;
    private final FieldType<T> type;

    public ConfigEntry(@Nullable String comment, @Nullable Category category, String key, T defaultValue, FieldType<T> type) {
        this.comment = comment;
        this.category = category;
        this.key = key;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.type = type;
    }


    public String fullKey() {
        return category == null ? key : category.name() + "." + key;
    }

    public @Nullable String getComment() {
        return comment;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String formatValue() {
        return type.format(value);
    }

    public void parseValue(String string) {
        this.value = type.parse(string);
    }


    public record Category(String name) {}
}
