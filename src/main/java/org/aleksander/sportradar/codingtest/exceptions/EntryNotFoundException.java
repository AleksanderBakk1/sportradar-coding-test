package org.aleksander.sportradar.codingtest.exceptions;

public class EntryNotFoundException extends Exception {
    public EntryNotFoundException(final String message) {
        super(message);
    }

    public EntryNotFoundException(final String message, final Exception cause) {
        super(message, cause);
    }
}
