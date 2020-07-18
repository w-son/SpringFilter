package com.son.SpringFilter.config;

import com.son.SpringFilter.security.filter.JwtAuthFilter;
import com.son.SpringFilter.security.provider.AccountProvider;
import com.son.SpringFilter.security.handler.LoginSuccessHandler;
import com.son.SpringFilter.security.filter.AccountLoginFilter;
import com.son.SpringFilter.security.provider.JwtProvider;
import com.son.SpringFilter.security.util.FilterMatcher;
import com.son.SpringFilter.security.util.JwtExtracter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccountProvider accountProvider;
    private final LoginSuccessHandler loginSuccessHandler;

    private final JwtProvider jwtProvider;
    private final JwtExtracter extracter;

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(accountProvider)
            .authenticationProvider(jwtProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests().antMatchers("/h2-console**").permitAll();

        /*
         정말 중요한 부분!!
         Security Filter가 적용시킬 Provider의 authenticate 메서드를 찾는 과정에서
         Authentication으로 넘어가는 PreAuthToken의 종류에 따라서 실행되기 때문에

         각각의 authenticate를 Override 하는 과정에서
         동일한 토큰의 형식을 넘겨주는 방식으로 Override하게 된다면
         Filtermatcher의 url 설정에 상관 없이 두 필터 모두 적용되게 된다
         */
        http.addFilterBefore(accountLoginFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    protected AccountLoginFilter accountLoginFilter() throws Exception {
        FilterMatcher matcher = new FilterMatcher(Arrays.asList("/api/v1/**"), Arrays.asList("/hello"));
        AccountLoginFilter filter = new AccountLoginFilter(matcher, loginSuccessHandler);
        filter.setAuthenticationManager(authenticationManager());

        return filter;
    }

    protected JwtAuthFilter jwtAuthFilter() throws Exception {
        FilterMatcher matcher = new FilterMatcher(Arrays.asList("/hello"), Arrays.asList("/", "/api/v1/**"));
        JwtAuthFilter filter = new JwtAuthFilter(matcher, extracter);
        filter.setAuthenticationManager(authenticationManager());

        return filter;
    }

    /**************************************************/
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("http://your-front-server:1234");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", configuration);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return bean;
    }
    /**************************************************/
}
