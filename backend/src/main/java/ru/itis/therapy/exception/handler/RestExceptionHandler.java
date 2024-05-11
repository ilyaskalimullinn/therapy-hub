package ru.itis.therapy.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.itis.therapy.dto.response.ExceptionResponse;
import ru.itis.therapy.exception.PasswordsNotMatchException;
import ru.itis.therapy.exception.SpecialistNotFoundException;
import ru.itis.therapy.exception.UserAlreadyExistsException;
import ru.itis.therapy.exception.UserNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ExceptionResponse.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionResponse.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(PasswordsNotMatchException.class)
    public ResponseEntity<ExceptionResponse> handleUPasswordsNotMatchException(PasswordsNotMatchException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ExceptionResponse.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(SpecialistNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleSpecialistNotFoundException(SpecialistNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionResponse.builder()
                        .message(e.getMessage())
                        .build());
    }
}
