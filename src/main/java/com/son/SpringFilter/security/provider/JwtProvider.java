package com.son.SpringFilter.security.provider;

import com.son.SpringFilter.security.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider implements AuthenticationProvider {

    private final JwtDecoder decoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreAuthJwtToken token = (PreAuthJwtToken) authentication;

        AccountContext context = decoder.validate(token.getToken());
        return new PostAuthToken(context);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PreAuthJwtToken.class.isAssignableFrom(aClass);
    }

}
