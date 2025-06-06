package net.mrwooly357.wool.config.field_type.custom;

import net.mrwooly357.wool.config.field_type.FieldType;

public class BooleanFieldType implements FieldType<Boolean> {

    public BooleanFieldType() {}


    @Override
    public Boolean parse(String string) {
        return Boolean.parseBoolean(string.trim());
    }

    @Override
    public String format(Boolean value) {
        return value.toString();
    }
}
