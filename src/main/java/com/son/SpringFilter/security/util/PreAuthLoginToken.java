package com.son.SpringFilter.security.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PreAuthLoginToken extends UsernamePasswordAuthenticationToken {

    // AccountLoginFilter를 사용하는 경우

    public PreAuthLoginToken(String username, String password) {
        super(username, password);
    }

    public String getUsername() { return (String) super.getPrincipal(); }

    public String getPassword() { return (String) super.getCredentials(); }

}
