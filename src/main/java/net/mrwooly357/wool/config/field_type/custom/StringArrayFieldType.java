package net.mrwooly357.wool.config.field_type.custom;

import net.mrwooly357.wool.config.field_type.FieldType;

public class StringArrayFieldType implements FieldType<String[]> {

    public StringArrayFieldType() {}


    @Override
    public String[] parse(String string) {
        if (string.isEmpty()) return new String[0];

        String[] parts = string.split(",");

        for (int a = 0; a < parts.length; a++) {
            parts[a] = parts[a].trim();
        }

        return parts;
    }

    @Override
    public String format(String[] value) {
        if (value == null || value.length == 0) return "";

        return String.join(", ", value);
    }
}
