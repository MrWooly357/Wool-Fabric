package net.mrwooly357.wool.config.field_type.custom;

import net.mrwooly357.wool.config.field_type.FieldType;

public class DoubleArrayFieldType implements FieldType<double[]> {

    public DoubleArrayFieldType() {}


    @Override
    public double[] parse(String string) {
        if (string.isEmpty()) return new double[0];

        String[] parts = string.split(",");
        double[] array = new double[parts.length];

        for (int a = 0; a < parts.length; a++) {
            array[a] = Double.parseDouble(parts[a].trim());
        }

        return array;
    }

    @Override
    public String format(double[] value) {
        if (value == null || value.length == 0) return "";

        StringBuilder builder = new StringBuilder();

        builder.append(value[0]);

        for (int a = 1; a < value.length; a++) {
            builder.append(", ").append(value[a]);
        }

        return builder.toString();
    }
}
