package com.son.SpringFilter.domain;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Optional;

public enum UserRole {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    public SimpleGrantedAuthority toAuth() {
        return new SimpleGrantedAuthority(this.role);
    }

    // TODO 이부분 뭔지 정확히 파악
    private boolean isCorrectRole(String role) {
        return role.equalsIgnoreCase(this.role);
    }

    public static Optional<UserRole> findRole(String role) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.isCorrectRole(role))
                .findFirst();
    }

}
