package com.son.SpringFilter.exception;

public class InvalidJwtException extends RuntimeException {

    private static final String ERROR_MESSAGE = "조작되었을 가능성이 있는 토큰 유형입니다";

    public InvalidJwtException() {
        super(ERROR_MESSAGE);
    }

}
