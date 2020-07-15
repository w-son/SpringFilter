package com.son.SpringFilter.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.son.SpringFilter.domain.UserRole;
import com.son.SpringFilter.exception.InvalidJwtException;
import com.son.SpringFilter.exception.RoleNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class JwtDecoder {

    @Value("${spring.jwt.signature}")
    private String signature;

    public AccountContext validate(String token) {

        DecodedJWT jwt = decode(token).orElseThrow(InvalidJwtException::new);

        // 필요한 정보들을 다 꺼낸다
        Long id = jwt.getClaim("USER_ID").asLong();
        String principal = jwt.getClaim("USERNAME").asString();
        String credentials = "Password는 대칭키 검증을 통해서 인증이 이루어졌다고 가정하고 임의의 값을 넣어 credential에 넣어줍니다";
        UserRole role = UserRole.findRole(jwt.getClaim("USER_ROLE").asString()).orElseThrow(RoleNotFoundException::new);
        List<SimpleGrantedAuthority> authorities = Stream.of(role).map(UserRole::toAuth).collect(Collectors.toList());

        Date exp = jwt.getClaim("exp").asDate();
        Date now = new Date();

        if(now.after(exp)) {
            throw new TokenExpiredException("유효 기간이 만료된 토큰입니다");
        }

        return new AccountContext(id, principal, credentials, authorities);
    }

    // 토큰을 검증하고 유효하지 않은 토큰이라면 InvalidJwtException을 던진다
    private Optional<DecodedJWT> decode(String token) {

        DecodedJWT jwt = null;
        try {

            Algorithm algorithm = Algorithm.HMAC256(signature);
            JWTVerifier verifier = JWT.require(algorithm).build();
            jwt = verifier.verify(token);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return Optional.ofNullable(jwt);
    }

}
