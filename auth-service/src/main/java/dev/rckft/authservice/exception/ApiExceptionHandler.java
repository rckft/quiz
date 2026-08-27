package dev.rckft.authservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Clock;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);
    private final Clock clock;

    public ApiExceptionHandler(Clock clock) {
        this.clock = clock;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        HttpStatus status = BAD_REQUEST;
        LOGGER.error(exception.getMessage(), exception);
        return ResponseEntity.status(status).body(getErrorResponse(status, exception));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException exception) {
        HttpStatus status = UNAUTHORIZED;
        LOGGER.error(exception.getMessage(), exception);
        return ResponseEntity.status(status).body(getErrorResponse(status, exception));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        HttpStatus status = UNAUTHORIZED;
        LOGGER.error(exception.getMessage(), exception);
        return ResponseEntity.status(status).body(getErrorResponse(status, exception));
    }

    @ExceptionHandler(PasswordsDontMatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordsDontMatchException(PasswordsDontMatchException exception) {
        HttpStatus status = BAD_REQUEST;
        LOGGER.error(exception.getMessage(), exception);
        return ResponseEntity.status(status).body(getErrorResponse(status, exception));
    }

    private ErrorResponse getErrorResponse(HttpStatus status, Exception exception) {
        return new ErrorResponse(
                status.getReasonPhrase(),
                exception.getMessage(),
                status.value(),
                clock.instant()
        );
    }

}
