package net.mrwooly357.wool.config.field_type;

import net.mrwooly357.wool.config.field_type.custom.*;

public interface FieldType<T> {

    FieldType<Integer> INTEGER = new IntegerFieldType();
    FieldType<Short> SHORT = new ShortFieldType();
    FieldType<Long> LONG = new LongFieldType();
    FieldType<Float> FLOAT = new FloatFieldType();
    FieldType<Double> DOUBLE = new DoubleFieldType();
    FieldType<Boolean> BOOLEAN = new BooleanFieldType();
    FieldType<String> STRING = new StringFieldType();
    FieldType<int[]> INTEGER_ARRAY = new IntegerArrayFieldType();
    FieldType<short[]> SHORT_ARRAY = new ShortArrayFieldType();
    FieldType<long[]> LONG_ARRAY = new LongArrayFieldType();
    FieldType<float[]> FLOAT_ARRAY = new FloatArrayFieldType();
    FieldType<double[]> DOUBLE_ARRAY = new DoubleArrayFieldType();
    FieldType<boolean[]> BOOLEAN_ARRAY = new BooleanArrayFieldType();
    FieldType<String[]> STRING_ARRAY = new StringArrayFieldType();


    T parse(String string);

    String format(T value);
}
