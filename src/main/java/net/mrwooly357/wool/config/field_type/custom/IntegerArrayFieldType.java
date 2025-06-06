package net.mrwooly357.wool.config.field_type.custom;

import net.mrwooly357.wool.config.field_type.FieldType;

public class IntegerArrayFieldType implements FieldType<int[]> {

    public IntegerArrayFieldType() {}


    @Override
    public int[] parse(String string) {
        if (string.isEmpty()) return new int[0];

        String[] parts = string.split(",");
        int[] array = new int[parts.length];

        for (int a = 0; a < parts.length; a++) {
            array[a] = Integer.parseInt(parts[a].trim());
        }

        return array;
    }

    @Override
    public String format(int[] value) {
        if (value == null || value.length == 0) return "";

        StringBuilder builder = new StringBuilder();

        builder.append(value[0]);

        for (int a = 1; a < value.length; a++) {
            builder.append(", ").append(value[a]);
        }

        return builder.toString();
    }
}
