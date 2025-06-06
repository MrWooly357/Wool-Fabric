package net.mrwooly357.wool.config.field_type.custom;

import net.mrwooly357.wool.config.field_type.FieldType;

public class StringFieldType implements FieldType<String> {

    public StringFieldType() {}


    @Override
    public String parse(String string) {
        return string;
    }

    @Override
    public String format(String value) {
        return value;
    }
}
