package net.mrwooly357.wool.config.field_type.custom;

import net.mrwooly357.wool.config.field_type.FieldType;

public class BooleanArrayFieldType implements FieldType<boolean[]> {

    public BooleanArrayFieldType() {}


    @Override
    public boolean[] parse(String string) {
        if (string.isEmpty()) return new boolean[0];

        String[] parts = string.split(",");
        boolean[] array = new boolean[parts.length];

        for (int a = 0; a < parts.length; a++) {
            array[a] = Boolean.parseBoolean(parts[a].trim());
        }

        return array;
    }

    @Override
    public String format(boolean[] value) {
        if (value == null || value.length == 0) return "";

        StringBuilder builder = new StringBuilder();

        builder.append(value[0]);

        for (int a = 1; a < value.length; a++) {
            builder.append(", ").append(value[a]);
        }

        return builder.toString();
    }
}
