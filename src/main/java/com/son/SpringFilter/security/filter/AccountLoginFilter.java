package com.son.SpringFilter.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.son.SpringFilter.dto.AccountRequestDto;
import com.son.SpringFilter.security.token.PreAuthLoginToken;
import com.son.SpringFilter.security.util.RequestWrapper;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.naming.AuthenticationNotSupportedException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountLoginFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationSuccessHandler handler;

    public AccountLoginFilter(RequestMatcher matcher, AuthenticationSuccessHandler handler) {
        super(matcher);
        this.handler = handler;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // AbstractAuthenticationProcessingFilter 의 doFilter에서 이 메서드를 호출하게 된다
        String method = request.getMethod();
        if(!method.equals("POST")) {
            throw new AuthenticationNotSupportedException("Authentication method not supported : " + method);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        AccountRequestDto dto = objectMapper.readValue(requestWrapper.getReader(), AccountRequestDto.class);

        return super.getAuthenticationManager().authenticate(new PreAuthLoginToken(dto.getUsername(), dto.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        handler.onAuthenticationSuccess(request, response, auth);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

}
