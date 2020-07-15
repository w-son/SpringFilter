package com.son.SpringFilter.security.util;

import com.son.SpringFilter.domain.Account;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 SecurityContext에 인증정보를 저장 할 때
 상황에 따라 principal에 단순 UserDetails 뿐만 아니라 AccountId나 UserGroup등의 정보를 저장하고 싶은 경우

 User 인터페이스를 상속 받는 클래스를 정의하여 (AccountContext)
 이 객체를 principal에 저장해 주고 토큰을 SecurityContext에 저장하면 된다
 **/
public class PostAuthToken extends UsernamePasswordAuthenticationToken {

    private PostAuthToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    /*
     SecurityContext에 인증 정보를 저장하지 않는 경우
     초기에 AccountLoginFilter를 통해 인증을 하는 경우이다
     */
    public PostAuthToken(Account account, List<SimpleGrantedAuthority> authorities) {
        this(account, account.getPassword(), authorities);
    }
    public Account getAccount() {
        return (Account) super.getPrincipal();
    }

    /*
     SecurityContext에 인증 정보를 저장하는 경우
     JwtFilter를 통해 토큰 검증을 하고
     세션 사용자의 정보를 SecurityContext에 저장하는 경우이다
     */
    public PostAuthToken(AccountContext accountContext) {
        this(accountContext, accountContext.getPassword(), accountContext.getAuthorities());
    }
    public AccountContext getAccountContext() { return (AccountContext) super.getPrincipal(); }

}
