package dev.rckft.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        HttpStatus status = BAD_REQUEST;
        return ResponseEntity.status(status).body(getErrorResponse(status, exception));
    }

    private ErrorResponse getErrorResponse(HttpStatus status, Exception exception) {
        return new ErrorResponse(
                status.getReasonPhrase(),
                exception.getMessage(),
                status.value(),
                LocalDateTime.now()
        );
    }

}
