package ru.hse.se.ucheck;

public class UCheckException extends Exception {

    public UCheckException() {
        super();
    }

    public UCheckException(String message) {
        super(message);
    }

    public UCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public UCheckException(Throwable cause) {
        super(cause);
    }

    protected UCheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
