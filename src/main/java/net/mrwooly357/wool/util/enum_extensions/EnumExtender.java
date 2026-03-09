package net.mrwooly357.wool.util.enum_extensions;

import com.mojang.datafixers.util.Pair;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

public final class EnumExtender<E extends Enum<E>> {

    private final Class<E> baseClass;
    private final Class<? extends E> referenceClass;
    private final Field[] fields;
    private final Consumer<EnumExtender<E>> onAdded;

    private EnumExtender(Class<E> baseClass, Class<? extends E> referenceClass, Consumer<EnumExtender<E>> onAdded, List<Field> fields) {
        this.baseClass = baseClass;
        this.referenceClass = referenceClass;
        this.onAdded = onAdded;
        this.fields = fields.toArray(new Field[0]);
    }


    public static <E extends Enum<E>> EnumExtender<E> of(Class<E> baseClass, Class<? extends E> referenceClass, Consumer<EnumExtender<E>> onAdded, List<Field> fields) {
        return new EnumExtender<>(baseClass, referenceClass, onAdded, fields);
    }

    public static <E extends Enum<E>> EnumExtender<E> of(Class<E> baseClass, Class<? extends E> referenceClass, List<Field> fields) {
        return new EnumExtender<>(baseClass, referenceClass, extender -> {}, fields);
    }

    public static <E extends Enum<E>> EnumExtender<E> of(Class<E> baseClass, Consumer<EnumExtender<E>> onAdded, List<Field> fields) {
        return of(baseClass, baseClass, onAdded, fields);
    }

    public static <E extends Enum<E>> EnumExtender<E> of(Class<E> baseClass, List<Field> fields) {
        return of(baseClass, baseClass, extender -> {}, fields);
    }

    public static <E extends Enum<E>> EnumExtender<E> of(Class<E> baseClass, Class<? extends E> referenceClass, Consumer<EnumExtender<E>> onAdded, Field... fields) {
        return of(baseClass, referenceClass, onAdded, List.of(fields));
    }

    public static <E extends Enum<E>> EnumExtender<E> of(Class<E> baseClass, Class<? extends E> referenceClass, Field... fields) {
        return of(baseClass, referenceClass, extender -> {}, List.of(fields));
    }

    public static <E extends Enum<E>> EnumExtender<E> of(Class<E> baseClass, Consumer<EnumExtender<E>> onAdded, Field... fields) {
        return of(baseClass, baseClass, onAdded, List.of(fields));
    }

    public static <E extends Enum<E>> EnumExtender<E> of(Class<E> baseClass, Field... fields) {
        return of(baseClass, baseClass, extender -> {}, List.of(fields));
    }

    public static <E extends Enum<E>> EnumExtender<E> create(Class<E> baseClass, Class<? extends E> referenceClass, Consumer<EnumExtender<E>> onAdded, List<Pair<Class<?>, Integer>> fields) {
        return new EnumExtender<>(baseClass, referenceClass, onAdded, fields.stream()
                .map(pair -> {
                    Field[] fields1 = baseClass.getDeclaredFields();
                    Class<?> type = pair.getFirst();
                    int ordinal = pair.getSecond();

                    for (Field field : fields1) {

                        if (field.getType().equals(type) && ordinal-- == 0)
                            return field;
                    }

                    throw new IllegalStateException("Couldn't find field with type " + type + " and ordinal " + ordinal + "!");
                })
                .toList());
    }

    public static <E extends Enum<E>> EnumExtender<E> create(Class<E> baseClass, Class<? extends E> referenceClass, List<Pair<Class<?>, Integer>> fields) {
        return new EnumExtender<>(baseClass, referenceClass, extender -> {}, fields.stream()
                .map(pair -> {
                    Field[] fields1 = baseClass.getDeclaredFields();
                    Class<?> type = pair.getFirst();
                    int ordinal = pair.getSecond();

                    for (Field field : fields1) {

                        if (field.getType().equals(type) && ordinal-- == 0)
                            return field;
                    }

                    throw new IllegalStateException("Couldn't find field with type " + type + " and ordinal " + ordinal + "!");
                })
                .toList());
    }

    public static <E extends Enum<E>> EnumExtender<E> create(Class<E> baseClass, Consumer<EnumExtender<E>> onAdded, List<Pair<Class<?>, Integer>> fields) {
        return create(baseClass, baseClass, onAdded, fields);
    }

    public static <E extends Enum<E>> EnumExtender<E> create(Class<E> baseClass, List<Pair<Class<?>, Integer>> fields) {
        return create(baseClass, baseClass, extender -> {}, fields);
    }

    @SafeVarargs
    public static <E extends Enum<E>> EnumExtender<E> create(Class<E> baseClass, Class<? extends E> referenceClass, Consumer<EnumExtender<E>> onAdded, Pair<Class<?>, Integer>... fields) {
        return create(baseClass, referenceClass, onAdded, List.of(fields));
    }

    @SafeVarargs
    public static <E extends Enum<E>> EnumExtender<E> create(Class<E> baseClass, Class<? extends E> referenceClass, Pair<Class<?>, Integer>... fields) {
        return create(baseClass, referenceClass, extender -> {}, List.of(fields));
    }

    @SafeVarargs
    public static <E extends Enum<E>> EnumExtender<E> create(Class<E> baseClass, Consumer<EnumExtender<E>> onAdded, Pair<Class<?>, Integer>... fields) {
        return create(baseClass, baseClass, onAdded, List.of(fields));
    }

    @SafeVarargs
    public static <E extends Enum<E>> EnumExtender<E> create(Class<E> baseClass, Pair<Class<?>, Integer>... fields) {
        return create(baseClass, baseClass, extender -> {}, List.of(fields));
    }

    public E add(String name, Consumer<E> initializer, Object... arguments) {
        E instance = EnumOperations.createInstance(baseClass, referenceClass, name.toUpperCase(Locale.ROOT));
        int fieldCount = fields.length;

        for (int i = 0; i < fieldCount; i++) {
            Field field = fields[i];
            Object value = arguments[i];
            Class<?> type = field.getType();

            if (type == byte.class)
                EnumOperations.setByteFieldValue(field, instance, (byte) value);
            else if (type == short.class)
                EnumOperations.setShortFieldValue(field, instance, (short) value);
            else if (type == int.class)
                EnumOperations.setIntFieldValue(field, instance, (int) value);
            else if (type == long.class)
                EnumOperations.setLongFieldValue(field, instance, (long) value);
            else if (type == float.class)
                EnumOperations.setFloatFieldValue(field, instance, (float) value);
            else if (type == double.class)
                EnumOperations.setDoubleFieldValue(field, instance, (double) value);
            else if (type == boolean.class)
                EnumOperations.setBooleanFieldValue(field, instance, (boolean) value);
            else if (type == char.class)
                EnumOperations.setCharFieldValue(field, instance, (char) value);
            else
                EnumOperations.setObjectFieldValue(field, instance, value);
        }

        initializer.accept(instance);
        onAdded.accept(this);

        return instance;
    }

    public void apply(Class<?>... switchUsers) {
        EnumOperations.updateSwitches(baseClass, switchUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseClass, referenceClass, Arrays.hashCode(fields), onAdded);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) || (other instanceof EnumExtender<?> extender
                && baseClass.equals(extender.baseClass)
                && referenceClass.equals(extender.referenceClass)
                && Arrays.equals(fields, extender.fields)
                && onAdded.equals(extender.onAdded));
    }

    @Override
    public String toString() {
        return "EnumExtender[base_class: " + baseClass
                + ", reference_class: " + referenceClass
                + ", fields: " + Arrays.toString(fields)
                + ", on_added: " + onAdded + "]";
    }
}
