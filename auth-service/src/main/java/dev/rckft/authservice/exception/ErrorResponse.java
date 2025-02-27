package dev.rckft.authservice.exception;

import java.time.LocalDateTime;

public record ErrorResponse (String error, String message, int status, LocalDateTime timestamp) {}