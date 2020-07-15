package com.son.SpringFilter.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.son.SpringFilter.domain.Account;
import com.son.SpringFilter.exception.InternalServerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtGenerator {

    @Value("${spring.jwt.signature}")
    private String signature;

    public String create(Account account) {

        String accessToken = Strings.EMPTY;

        try {

            Algorithm algorithm = Algorithm.HMAC256(signature);
            accessToken = JWT.create()
                    .withClaim("USER_ID", account.getId())
                    .withClaim("USERNAME", account.getUsername())
                    .withClaim("USER_ROLE", account.getRole().getRole())
                    .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)))
                    .sign(algorithm);

        } catch (Exception e) {
            throw new InternalServerException("Internal Server Error", e);
        }

        return accessToken;
    }

}
