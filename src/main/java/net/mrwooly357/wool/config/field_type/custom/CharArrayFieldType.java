package net.mrwooly357.wool.config.field_type.custom;

import net.mrwooly357.wool.config.field_type.FieldType;

import java.util.Arrays;

public class CharArrayFieldType implements FieldType<char[]> {

    public CharArrayFieldType() {}


    @Override
    public char[] parse(String string) {
        if (string.isEmpty()) return new char[0];

        String[] parts = string.split(", ");

        return Arrays.toString(parts).toCharArray();
    }

    @Override
    public String format(char[] value) {
        if (value == null || value.length == 0) return "";

        StringBuilder builder = new StringBuilder();

        builder.append(value[0]);

        for (int a = 1; a < value.length; a++) {
            builder.append(", ").append(value[a]);
        }

        return builder.toString();
    }
}
