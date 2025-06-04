package dev.rckft.authservice.exception;

public class PasswordsDontMatchException extends RuntimeException {
    private static final String MESSAGE = "Passwords dont match";

    public PasswordsDontMatchException() {
        super(MESSAGE);
    }
}