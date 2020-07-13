package com.son.SpringFilter.domain;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

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

}
