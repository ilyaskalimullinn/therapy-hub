package ru.itis.therapy.exception;

public class SpecialistNotFoundException extends RuntimeException {
    public SpecialistNotFoundException() {
        super();
    }

    public SpecialistNotFoundException(String message) {
        super(message);
    }

    public SpecialistNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
