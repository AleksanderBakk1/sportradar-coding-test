package org.aleksander.sportradar.codingtest.exceptions;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(final String message) {
        super(message);
    }
}
