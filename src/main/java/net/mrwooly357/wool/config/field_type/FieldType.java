package net.mrwooly357.wool.config.field_type;

import net.mrwooly357.wool.config.field_type.custom.*;

public interface FieldType<T> {

    FieldType<Byte> BYTE = new ByteFieldType();
    FieldType<Short> SHORT = new ShortFieldType();
    FieldType<Integer> INT = new IntFieldType();
    FieldType<Long> LONG = new LongFieldType();
    FieldType<Float> FLOAT = new FloatFieldType();
    FieldType<Double> DOUBLE = new DoubleFieldType();
    FieldType<Boolean> BOOLEAN = new BooleanFieldType();
    FieldType<Character> CHAR = new CharFieldType();
    FieldType<String> STRING = new StringFieldType();
    FieldType<byte[]> BYTE_ARRAY = new ByteArrayFieldType();
    FieldType<short[]> SHORT_ARRAY = new ShortArrayFieldType();
    FieldType<int[]> INT_ARRAY = new IntArrayFieldType();
    FieldType<long[]> LONG_ARRAY = new LongArrayFieldType();
    FieldType<float[]> FLOAT_ARRAY = new FloatArrayFieldType();
    FieldType<double[]> DOUBLE_ARRAY = new DoubleArrayFieldType();
    FieldType<boolean[]> BOOLEAN_ARRAY = new BooleanArrayFieldType();
    FieldType<char[]> CHAR_ARRAY = new CharArrayFieldType();
    FieldType<String[]> STRING_ARRAY = new StringArrayFieldType();


    T parse(String string);

    String format(T value);
}
