package net.mrwooly357.wool.config;

import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.config.field_type.FieldType;
import net.mrwooly357.wool.config.restriction.Restriction;
import org.jetbrains.annotations.Nullable;

public class ConfigEntry<T> {

    private final @Nullable Category category;
    private final String key;
    private T value;
    private final FieldType<T> type;
    private final @Nullable Restriction<T> restriction;

    public ConfigEntry(@Nullable Category category, String key, T defaultValue, FieldType<T> type, @Nullable Restriction<T> restriction) {
        this.category = category;
        this.key = key;
        this.value = defaultValue;
        this.type = type;
        this.restriction = restriction;
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
        T raw = type.parse(string);

        if (restriction != null && WoolConfig.enableRestrictions) {
            value = restriction.normalize(raw);
        } else {
            value = raw;
        }
    }


    public record Category(String name) {}
}
