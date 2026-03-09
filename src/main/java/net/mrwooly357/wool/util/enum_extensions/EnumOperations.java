package net.mrwooly357.wool.util.enum_extensions;

import net.mrwooly357.wool.util.unsafe.NotAnUnsafeUserException;
import net.mrwooly357.wool.util.unsafe.UnsafeLookup;
import net.mrwooly357.wool.util.unsafe.UnsafeUser;
import net.mrwooly357.wool.util.unsafe.UnsafeUtil;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

@UnsafeUser
public final class EnumOperations {

    private static final UnsafeLookup UNSAFE;

    private EnumOperations() {}


    static {
        try {
            UNSAFE = UnsafeUtil.getLookup(EnumOperations.class);
        } catch (NotAnUnsafeUserException e) {
            throw new RuntimeException("Couldn't access the UnsafeLookup instance!", e);
        }
    }

    @SuppressWarnings("unchecked")
    static <E extends Enum<E>> E createInstance(Class<E> baseClass, Class<? extends E> referenceClass, String name) {
        try {
            E instance = baseClass.cast(UNSAFE.allocateInstance(referenceClass));
            int fieldModifiers = Opcodes.ACC_STATIC | Opcodes.ACC_SYNTHETIC;
            Field[] fields = baseClass.getDeclaredFields();

            for (Field field : fields) {

                if (field.getType().isArray() && (field.getModifiers() & fieldModifiers) == fieldModifiers) {
                    field.setAccessible(true);

                    try {
                        E[] values = (E[]) field.get(null);
                        Arrays.stream(values)
                                .filter(e -> e.name().equals(name))
                                .findFirst()
                                .ifPresent(e -> {
                                    throw new IllegalArgumentException("Enum constant with name " + name + " already exists!");
                                });
                        E[] modifiedValues = Arrays.copyOf(values, values.length + 1);
                        int ordinal = modifiedValues.length - 1;
                        modifiedValues[ordinal] = instance;
                        setStaticObjectFieldValue(field, modifiedValues);
                        setObjectFieldValue(Enum.class.getDeclaredField("name"), instance, name);
                        setIntFieldValue(Enum.class.getDeclaredField("ordinal"), instance, ordinal);
                        findField("enumConstantDirectory").ifPresent(field1 -> setObjectFieldValue(field1, baseClass, null));
                        findField("enumConstants").ifPresent(field1 -> setObjectFieldValue(field1, baseClass, null));

                        break;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Couldn't access values array of " + baseClass + "!", e);
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException("Couldn't find required field!", e);
                    }
                }
            }

            return instance;
        } catch (InstantiationException e) {
            throw new RuntimeException("Couldn't instantiate " + baseClass + ", using " + referenceClass + " as a reference!", e);
        }
    }

    public static void setStaticObjectFieldValue(Field field, Object o) {
        Object base = UNSAFE.getStaticFieldBase(field);
        long offset = UNSAFE.getStaticFieldOffset(field);

        if (isFieldVolatile(field))
            UNSAFE.putObjectVolatile(base, offset, o);
        else
            UNSAFE.putObject(base, offset, o);
    }

    public static void setObjectFieldValue(Field field, Object instance, Object o) {
        long offset = getFieldOffset(field);

        if (isFieldVolatile(field))
            UNSAFE.putObjectVolatile(instance, offset, o);
        else
            UNSAFE.putObject(instance, offset, o);
    }

    public static void setByteFieldValue(Field field, Object instance, byte b) {
        long offset = getFieldOffset(field);

        if (isFieldVolatile(field))
            UNSAFE.putByteVolatile(instance, offset, b);
        else
            UNSAFE.putByte(instance, offset, b);
    }

    public static void setShortFieldValue(Field field, Object instance, short s) {
        long offset = getFieldOffset(field);

        if (isFieldVolatile(field))
            UNSAFE.putShortVolatile(instance, offset, s);
        else
            UNSAFE.putShort(instance, offset, s);
    }

    public static void setIntFieldValue(Field field, Object instance, int i) {
        long offset = getFieldOffset(field);

        if (isFieldVolatile(field))
            UNSAFE.putIntVolatile(instance, offset, i);
        else
            UNSAFE.putInt(instance, offset, i);
    }

    public static void setLongFieldValue(Field field, Object instance, long l) {
        long offset = getFieldOffset(field);

        if (isFieldVolatile(field))
            UNSAFE.putLongVolatile(instance, offset, l);
        else
            UNSAFE.putLong(instance, offset, l);
    }

    public static void setFloatFieldValue(Field field, Object instance, float f) {
        long offset = getFieldOffset(field);

        if (isFieldVolatile(field))
            UNSAFE.putFloatVolatile(instance, offset, f);
        else
            UNSAFE.putFloat(instance, offset, f);
    }

    public static void setDoubleFieldValue(Field field, Object instance, double d) {
        long offset = getFieldOffset(field);

        if (isFieldVolatile(field))
            UNSAFE.putDoubleVolatile(instance, offset, d);
        else
            UNSAFE.putDouble(instance, offset, d);
    }

    public static void setBooleanFieldValue(Field field, Object instance, boolean bl) {
        long offset = getFieldOffset(field);

        if (isFieldVolatile(field))
            UNSAFE.putBooleanVolatile(instance, offset, bl);
        else
            UNSAFE.putBoolean(instance, offset, bl);
    }

    public static void setCharFieldValue(Field field, Object instance, char c) {
        long offset = getFieldOffset(field);

        if (isFieldVolatile(field))
            UNSAFE.putCharVolatile(instance, offset, c);
        else
            UNSAFE.putChar(instance, offset, c);
    }

    private static boolean isFieldVolatile(Field field) {
        return Modifier.isVolatile(field.getModifiers());
    }

    private static long getFieldOffset(Field field) {
        return UNSAFE.getObjectFieldOffset(field);
    }

    private static Optional<Field> findField(String name) {
        Field[] fields = Class.class.getDeclaredFields();
        Class<?> clazz1 = Class.class;

        while (clazz1 != Object.class) {

            for (Field field : fields)

                if (field.getName().equals(name))
                    return Optional.of(field);

            clazz1 = clazz1.getSuperclass();
            fields = clazz1.getDeclaredFields();
        }

        return Optional.empty();
    }

    static void updateSwitches(Class<? extends Enum<?>> baseClass, Class<?>... switchUsers) {
        String asString = baseClass.getName().replace('.', '$');
        List<Field> fields = new ArrayList<>();
        Arrays.stream(switchUsers)
                .forEach(user ->
                        findAnonymousClasses(user).forEach(anonymous ->
                                Arrays.stream(anonymous.getDeclaredFields()).forEach(field -> {
                                    if (field.getName().startsWith("$SwitchMap$" + asString)) {
                                        field.setAccessible(true);
                                        fields.add(field);
                                    }
                                })));
        fields.forEach(field -> {
            try {
                int[] switches = (int[]) field.get(null);
                switches = Arrays.copyOf(switches, switches.length + 1);
                setStaticObjectFieldValue(field, switches);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static List<Class<?>> findAnonymousClasses(Class<?> clazz) {
        List<Class<?>> classes = new ArrayList<>();
        String name = clazz.getName();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        for (int i = 0; ; i++) {

            try {
                classes.add(Class.forName(name + "$" + i, false, classLoader));
            } catch (ClassNotFoundException e) {
                break;
            }
        }

        Class<?>[] innerClasses = clazz.getDeclaredClasses();

        for (Class<?> inner : innerClasses)
            classes.addAll(findAnonymousClasses(inner));

        List<Class<?>> asList = new ArrayList<>(classes);

        for (Class<?> anonymous : asList)
            classes.addAll(findAnonymousClasses(anonymous));

        return classes;
    }
}
