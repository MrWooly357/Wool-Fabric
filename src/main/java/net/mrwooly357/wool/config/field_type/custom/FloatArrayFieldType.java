package net.mrwooly357.wool.config.field_type.custom;

import net.mrwooly357.wool.config.field_type.FieldType;

public class FloatArrayFieldType implements FieldType<float[]> {

    public FloatArrayFieldType() {}


    @Override
    public float[] parse(String string) {
        if (string.isEmpty()) return new float[0];

        String[] parts = string.split(",");
        float[] array = new float[parts.length];

        for (int a = 0; a < parts.length; a++) {
            array[a] = Float.parseFloat(parts[a].trim());
        }

        return array;
    }

    @Override
    public String format(float[] value) {
        if (value == null || value.length == 0) return "";

        StringBuilder builder = new StringBuilder();

        builder.append(value[0]);

        for (int a = 1; a < value.length; a++) {
            builder.append(", ").append(value[a]);
        }

        return builder.toString();
    }
}
