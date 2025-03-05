package dev.rckft.authservice.exception;

public class InvalidTokenException extends RuntimeException {
    private static final String MESSAGE = "Token is either expired or revoked";

    public InvalidTokenException() { super(MESSAGE); }
}