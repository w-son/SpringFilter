package com.son.SpringFilter.security.util;

import com.son.SpringFilter.exception.InvalidHeaderException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenExtracter {

    private static final String HEADER_TYPE = "Authorization";
    private static final String HEADER_PREFIX = "Bearer ";

    public PreAuthJwtToken extract(HttpServletRequest request) {

        String header = request.getHeader(HEADER_TYPE);
        if(StringUtils.isEmpty(header)) {
            throw new InvalidHeaderException();
        }

        String prefix = header.substring(0, HEADER_PREFIX.length());
        if(!prefix.equals(HEADER_PREFIX)) {
            throw new InvalidHeaderException();
        }

        return new PreAuthJwtToken(header.substring(HEADER_PREFIX.length()));
    }

}
