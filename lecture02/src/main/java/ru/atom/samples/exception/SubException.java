package ru.atom.samples.exception;

public class SubException extends BaseException {

    public SubException(String message) {
        super("SubException: " + message);
    }
}
