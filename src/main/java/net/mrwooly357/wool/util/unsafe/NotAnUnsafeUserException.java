package net.mrwooly357.wool.util.unsafe;

public class NotAnUnsafeUserException extends Exception {

    public NotAnUnsafeUserException() {
        super();
    }

    public NotAnUnsafeUserException(String message) {
        super(message);
    }

    public NotAnUnsafeUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAnUnsafeUserException(Throwable cause) {
        super(cause);
    }
}
