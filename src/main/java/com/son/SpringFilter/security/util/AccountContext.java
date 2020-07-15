package com.son.SpringFilter.security.util;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AccountContext extends User {

    // PostAuthToken의 주석 참조
    private Long id;

    public AccountContext(Long id, String principal, String credentials,
                          Collection<? extends GrantedAuthority> authorities) {

        super(principal, credentials, authorities);
        this.id = id;
    }

}
