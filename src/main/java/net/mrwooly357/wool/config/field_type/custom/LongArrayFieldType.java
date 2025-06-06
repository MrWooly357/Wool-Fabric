package net.mrwooly357.wool.config.field_type.custom;

import net.mrwooly357.wool.config.field_type.FieldType;

public class LongArrayFieldType implements FieldType<long[]> {

    public LongArrayFieldType() {}


    @Override
    public long[] parse(String string) {
        if (string.isEmpty()) return new long[0];

        String[] parts = string.split(",");
        long[] array = new long[parts.length];

        for (int a = 0; a < parts.length; a++) {
            array[a] = Long.parseLong(parts[a].trim());
        }

        return array;
    }

    @Override
    public String format(long[] value) {
        if (value == null || value.length == 0) return "";

        StringBuilder builder = new StringBuilder();

        builder.append(value[0]);

        for (int a = 1; a < value.length; a++) {
            builder.append(", ").append(value[a]);
        }

        return builder.toString();
    }
}
