package com.son.SpringFilter.exception;

import javax.naming.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {

    private static final String ERROR_MESSAGE = "존재하지 않는 Username 입니다";

    public UserNotFoundException() { super(ERROR_MESSAGE);}

}
