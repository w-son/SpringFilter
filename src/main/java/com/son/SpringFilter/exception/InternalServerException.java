package com.son.SpringFilter.exception;

public class InternalServerException extends RuntimeException {

    public InternalServerException(String message, Throwable t) {
        super(message, t);
    }

}
