package net.mrwooly357.wool.config.field_type.custom;

import net.mrwooly357.wool.config.field_type.FieldType;

public class IntFieldType implements FieldType<Integer> {

    public IntFieldType() {}


    @Override
    public Integer parse(String string) {
        return Integer.parseInt(string.trim());
    }

    @Override
    public String format(Integer value) {
        return value.toString();
    }
}
