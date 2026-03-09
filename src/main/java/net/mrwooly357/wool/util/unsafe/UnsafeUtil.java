package net.mrwooly357.wool.util.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * Simple {@link Unsafe} and {@link UnsafeLookup} utilities.
 */
@UnsafeUser
public final class UnsafeUtil {

    /**
     * The {@link UnsafeLookup} instance to use for 'unsafe' operations.
     */
    private static final UnsafeLookup LOOKUP;

    /**
     * The constructor used to create new {@link UnsafeUtil} instances.
     * @apiNote Don't use it, as an {@link UnsupportedOperationException} will be thrown!
     * @throws UnsupportedOperationException if called.
     */
    private UnsafeUtil() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate UnsafeUtil!");
    }


    static {
        try {
            LOOKUP = UnsafeLookup.of(UnsafeUtil.class, createUnsafe());
        } catch (NotAnUnsafeUserException e) {
            throw new RuntimeException("Not an unsafe user!", e);
        }
    }

    /**
     * Tries to get or create an instance of {@link Unsafe}.
     * @return the {@link Unsafe} instance.
     */
    public static Unsafe createUnsafe() {
        Unsafe unsafe;

        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {

            try {
                Constructor<Unsafe> constructor = Unsafe.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                unsafe = constructor.newInstance();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Couldn't find Unsafe's no arguments constructor!", e);
            } catch (InstantiationException | IllegalArgumentException e) {
                throw new RuntimeException("Couldn't instantiate Unsafe using the no arguments constructor!", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Couldn't access Unsafe's no arguments constructor!", e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("An exception was thrown during the invocation of Unsafe's no arguments constructor!", e);
            }
        }

        return Objects.requireNonNull(unsafe, "The Unsafe instance is null!");
    }

    /**
     * Checks if the {@code user} is annotated as an {@link UnsafeUser}.
     * @param user the {@link Class class} to check.
     * @throws NotAnUnsafeUserException if the {@code user} isn't annotated as an {@link UnsafeUser}.
     */
    public static void checkUser(Class<?> user) throws NotAnUnsafeUserException {
        if (!user.isAnnotationPresent(UnsafeUser.class))
            throw new NotAnUnsafeUserException("The " + user + " isn't annotated as an unsafe user!");
    }

    /**
     * Tries to get the {@link UnsafeUtil#LOOKUP}.
     * @param user the {@link UnsafeLookup} user.
     * @return the {@link UnsafeUtil#LOOKUP} if
     * @throws NotAnUnsafeUserException if the {@code user} isn't annotated as an {@link UnsafeUser}.
     */
    public static UnsafeLookup getLookup(Class<?> user) throws NotAnUnsafeUserException {
        checkUser(user);

        return LOOKUP;
    }
}
