package com.son.SpringFilter.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.son.SpringFilter.domain.Account;
import com.son.SpringFilter.dto.AccountResponseDto;
import com.son.SpringFilter.exception.InternalServerException;
import com.son.SpringFilter.security.util.JwtGenerator;
import com.son.SpringFilter.security.util.PostAuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtGenerator jwtGenerator;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        PostAuthToken token = (PostAuthToken) auth;
        Account account = token.getAccount();

        String accessToken = jwtGenerator.create(account);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        try {
            String username = account.getUsername();
            AccountResponseDto dto = new AccountResponseDto(username, accessToken);

            response.getWriter().write(new ObjectMapper().writeValueAsString(dto));

        } catch (IOException e) {
            throw new InternalServerException("Internal Server Error", e);
        }
    }

}
