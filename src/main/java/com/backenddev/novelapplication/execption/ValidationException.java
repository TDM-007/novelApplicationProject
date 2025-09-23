package com.backenddev.novelapplication.execption;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
