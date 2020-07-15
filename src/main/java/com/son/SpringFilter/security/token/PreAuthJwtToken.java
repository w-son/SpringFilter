package com.son.SpringFilter.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreAuthJwtToken extends UsernamePasswordAuthenticationToken {

    // JwtAuthFilter를 사용하는 경우

    public PreAuthJwtToken(String token) {
        super("token", token);
    }

    public String getToken() {
        return (String) super.getCredentials();
    }

}
