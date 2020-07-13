package com.son.SpringFilter.exception;

import javax.naming.AuthenticationException;

public class CredentialErrorException extends AuthenticationException {

    private static final String ERROR_MESSAGE = "Password가 일치하지 않습니다";

    public CredentialErrorException() { super(ERROR_MESSAGE);}

}
