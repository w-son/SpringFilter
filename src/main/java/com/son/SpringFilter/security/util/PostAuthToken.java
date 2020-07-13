package com.son.SpringFilter.security.util;

import com.son.SpringFilter.domain.Account;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class PostAuthToken extends UsernamePasswordAuthenticationToken {

    private PostAuthToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public PostAuthToken(Account account, List<SimpleGrantedAuthority> authorities) {
        this(account, account.getPassword(), authorities);
    }

    public Account getAccount() {
        return (Account) super.getPrincipal();
    }

}
