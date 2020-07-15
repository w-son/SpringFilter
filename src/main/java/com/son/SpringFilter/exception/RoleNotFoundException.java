package com.son.SpringFilter.exception;

public class RoleNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "확인 되지 않은 ROLE 입니다";

    public RoleNotFoundException() {
        super(ERROR_MESSAGE);
    }

}
