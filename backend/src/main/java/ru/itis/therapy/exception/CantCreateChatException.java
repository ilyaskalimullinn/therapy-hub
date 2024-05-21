package ru.itis.therapy.exception;

public class CantCreateChatException extends RuntimeException {
    public CantCreateChatException() {
        super();
    }

    public CantCreateChatException(String message) {
        super(message);
    }

    public CantCreateChatException(String message, Throwable cause) {
        super(message, cause);
    }
}
