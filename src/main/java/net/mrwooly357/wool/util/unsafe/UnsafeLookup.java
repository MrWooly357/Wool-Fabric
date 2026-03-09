package net.mrwooly357.wool.util.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Objects;

public final class UnsafeLookup {

    private final Unsafe unsafe;

    private UnsafeLookup(Unsafe unsafe) {
        this.unsafe = unsafe;
    }


    public static UnsafeLookup of(Class<?> user, Unsafe unsafe) throws NotAnUnsafeUserException {
        UnsafeUtil.checkUser(user);
        Objects.requireNonNull(unsafe, "Unsafe is null!");

        return new UnsafeLookup(unsafe);
    }

    public Unsafe getUnsafe() {
        return unsafe;
    }

    public Object allocateInstance(Class<?> clazz) throws InstantiationException {
        return unsafe.allocateInstance(clazz);
    }

    @SuppressWarnings("deprecation")
    public Object getStaticFieldBase(Field field) {
        return unsafe.staticFieldBase(field);
    }

    @SuppressWarnings("deprecation")
    public long getStaticFieldOffset(Field field) {
        return unsafe.staticFieldOffset(field);
    }

    @SuppressWarnings("deprecation")
    public long getObjectFieldOffset(Field field) {
        return unsafe.objectFieldOffset(field);
    }

    public void putByte(Object instance, long offset, byte b) {
        unsafe.putByte(instance, offset, b);
    }

    public void putByteVolatile(Object instance, long offset, byte b) {
        unsafe.putByteVolatile(instance, offset, b);
    }

    public void putShort(Object instance, long offset, short s) {
        unsafe.putShort(instance, offset, s);
    }

    public void putShortVolatile(Object instance, long offset, short s) {
        unsafe.putShortVolatile(instance, offset, s);
    }

    public void putInt(Object instance, long offset, int i) {
        unsafe.putInt(instance, offset, i);
    }

    public void putIntVolatile(Object instance, long offset, int i) {
        unsafe.putIntVolatile(instance, offset, i);
    }

    public void putLong(Object instance, long offset, long l) {
        unsafe.putLong(instance, offset, l);
    }

    public void putLongVolatile(Object instance, long offset, long l) {
        unsafe.putLongVolatile(instance, offset, l);
    }

    public void putFloat(Object instance, long offset, float f) {
        unsafe.putFloat(instance, offset, f);
    }

    public void putFloatVolatile(Object instance, long offset, float f) {
        unsafe.putFloatVolatile(instance, offset, f);
    }

    public void putDouble(Object instance, long offset, double d) {
        unsafe.putDouble(instance, offset, d);
    }

    public void putDoubleVolatile(Object instance, long offset, double d) {
        unsafe.putDoubleVolatile(instance, offset, d);
    }

    public void putBoolean(Object instance, long offset, boolean bl) {
        unsafe.putBoolean(instance, offset, bl);
    }

    public void putBooleanVolatile(Object instance, long offset, boolean bl) {
        unsafe.putBooleanVolatile(instance, offset, bl);
    }

    public void putChar(Object instance, long offset, char c) {
        unsafe.putChar(instance, offset, c);
    }

    public void putCharVolatile(Object instance, long offset, char c) {
        unsafe.putCharVolatile(instance, offset, c);
    }

    public void putObject(Object instance, long offset, Object o) {
        unsafe.putObject(instance, offset, o);
    }

    public void putObjectVolatile(Object instance, long offset, Object o) {
        unsafe.putObjectVolatile(instance, offset, o);
    }

    @Override
    public int hashCode() {
        return unsafe.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof UnsafeLookup lookup
                && unsafe.equals(lookup.unsafe));
    }

    @Override
    public String toString() {
        return "UnsafeLookup[unsafe: " + unsafe + "]";
    }
}
