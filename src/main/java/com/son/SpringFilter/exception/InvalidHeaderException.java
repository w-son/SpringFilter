package com.son.SpringFilter.exception;

public class InvalidHeaderException extends RuntimeException {

    private static final String ERROR_MESSAGE = "헤더 형식이 올바르지 않습니다";

    public InvalidHeaderException() {
        super(ERROR_MESSAGE);
    }

}
