package net.mrwooly357.wool.config;

import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.config.field_type.FieldType;
import net.mrwooly357.wool.config.restriction.Restriction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

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
        return category == null ? key : category.getName() + "." + key;
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


    public static class Category {

        private final String name;
        private final List<Category> categories;

        public Category(String name) {
            this(name, null);
        }

        public Category(String name, @Nullable List<Category> categories) {
            this.name = name;
            this.categories = categories;
        }


        public String getName() {
            String name = this.name;

            if (categories != null) {
                List<String> additionalCategories = new ArrayList<>();
                StringBuilder builder = new StringBuilder();

                for (Category category : categories) {
                    additionalCategories.add(category.getName());
                }

                for (String additionalCategory : additionalCategories) {
                    builder.append(additionalCategory).append(".");
                }

                builder.append(name);

                name = builder.toString();
            }

            return name;
        }
    }
}
