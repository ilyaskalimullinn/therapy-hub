package ru.itis.therapy.exception;

public class SpecialtyNotFoundException extends RuntimeException {
    public SpecialtyNotFoundException() {
        super();
    }

    public SpecialtyNotFoundException(String message) {
        super(message);
    }

    public SpecialtyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
