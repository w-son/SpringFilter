package com.son.SpringFilter.security.util;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class FilterMatcher implements RequestMatcher {

    private OrRequestMatcher noAuth;
    private OrRequestMatcher withAuth;

    public FilterMatcher(List<String> noAuth, List<String> withAuth) {
        this.noAuth = new OrRequestMatcher(noAuth.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList()));
        this.withAuth = new OrRequestMatcher(withAuth.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList()));
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return !noAuth.matches(request) && withAuth.matches(request);
    }

}
