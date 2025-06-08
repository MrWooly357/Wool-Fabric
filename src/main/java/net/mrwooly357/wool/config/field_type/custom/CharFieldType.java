package net.mrwooly357.wool.config.field_type.custom;

import net.mrwooly357.wool.config.field_type.FieldType;

public class CharFieldType implements FieldType<Character> {

    public CharFieldType() {}


    @Override
    public Character parse(String string) {
        return string.charAt(0);
    }

    @Override
    public String format(Character value) {
        return value.toString();
    }
}
