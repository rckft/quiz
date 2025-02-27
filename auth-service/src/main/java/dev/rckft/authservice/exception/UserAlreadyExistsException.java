package dev.rckft.authservice.exception;

public class UserAlreadyExistsException extends Exception {
    private static final String MESSAGE = "User already exists";

    public UserAlreadyExistsException() {
        super(MESSAGE);
    }
}