package com.son.SpringFilter.config;

import com.son.SpringFilter.security.provider.AccountProvider;
import com.son.SpringFilter.security.handler.LoginSuccessHandler;
import com.son.SpringFilter.security.filter.AccountLoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccountProvider accountProvider;
    private final LoginSuccessHandler loginSuccessHandler;

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(accountProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests().antMatchers("/h2-console**").permitAll();

        http.addFilterAt(accountLoginFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    protected AccountLoginFilter accountLoginFilter() throws Exception {
        AccountLoginFilter filter = new AccountLoginFilter("/hello", loginSuccessHandler);
        filter.setAuthenticationManager(authenticationManager());

        return filter;
    }

}
