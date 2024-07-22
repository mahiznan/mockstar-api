package com.span.mockstar.config.exception;

public class InternalServerError extends Exception {
    public InternalServerError() {
    }

    public InternalServerError(String message) {
        super(message);
    }

    public InternalServerError(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "InternalServerError: " + getMessage();
    }
}
