package org.vitej.core.exception;

public class ActionNotSupportedException extends RuntimeException {
    public ActionNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}
