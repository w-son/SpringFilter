package com.son.SpringFilter.security.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PreAuthToken extends UsernamePasswordAuthenticationToken {

    public PreAuthToken(String username, String password) {
        super(username, password);
    }

    public String getUsername() { return (String) super.getPrincipal(); }

    public String getPassword() { return (String) super.getCredentials(); }

}
