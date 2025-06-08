package net.mrwooly357.wool.config.field_type.custom;

import net.mrwooly357.wool.config.field_type.FieldType;

public class ByteArrayFieldType implements FieldType<byte[]> {

    public ByteArrayFieldType() {}


    @Override
    public byte[] parse(String string) {
        if (string.isEmpty()) return new byte[0];

        String[] parts = string.split(",");
        byte[] array = new byte[parts.length];

        for (int a = 0; a < parts.length; a++) {
            array[a] = Byte.parseByte(parts[a].trim());
        }

        return array;
    }

    @Override
    public String format(byte[] value) {
        if (value == null || value.length == 0) return "";

        StringBuilder builder = new StringBuilder();

        builder.append(value[0]);

        for (int a = 1; a < value.length; a++) {
            builder.append(", ").append(value[a]);
        }

        return builder.toString();
    }
}
