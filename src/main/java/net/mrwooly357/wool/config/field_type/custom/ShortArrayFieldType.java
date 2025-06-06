package net.mrwooly357.wool.config.field_type.custom;

import net.mrwooly357.wool.config.field_type.FieldType;

public class ShortArrayFieldType implements FieldType<short[]> {

    public ShortArrayFieldType() {}


    @Override
    public short[] parse(String string) {
        if (string.isEmpty()) return new short[0];

        String[] parts = string.split(",");
        short[] array = new short[parts.length];

        for (int a = 0; a < parts.length; a++) {
            array[a] = Short.parseShort(parts[a].trim());
        }

        return array;
    }

    @Override
    public String format(short[] value) {
        if (value == null || value.length == 0) return "";

        StringBuilder builder = new StringBuilder();

        builder.append(value[0]);

        for (int a = 1; a < value.length; a++) {
            builder.append(", ").append(value[a]);
        }

        return builder.toString();
    }
}
