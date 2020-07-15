package com.son.SpringFilter.security.provider;

import com.son.SpringFilter.domain.Account;
import com.son.SpringFilter.domain.UserRole;
import com.son.SpringFilter.exception.CredentialErrorException;
import com.son.SpringFilter.exception.UserNotFoundException;
import com.son.SpringFilter.security.token.PostAuthToken;
import com.son.SpringFilter.security.token.PreAuthLoginToken;
import com.son.SpringFilter.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class AccountProvider implements AuthenticationProvider {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @SneakyThrows
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreAuthLoginToken token = (PreAuthLoginToken) authentication;

        /*
         존재하는 username인지, username에 해당하는 password가 정확한지 확인
         해당 되지 않을 시에 CredentialErrorException을 throw 한다
         */
        Account account = accountService.findByUsername(token.getUsername()).orElseThrow(UserNotFoundException::new);
        if(passwordEncoder.matches(token.getPassword(), account.getPassword())) {
            List<SimpleGrantedAuthority> authorities =
                    Stream.of(account.getRole()).map(UserRole::toAuth).collect(Collectors.toList());

            return new PostAuthToken(account, authorities);

        } else {
            throw new CredentialErrorException();
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PreAuthLoginToken.class.isAssignableFrom(aClass);
    }

}
