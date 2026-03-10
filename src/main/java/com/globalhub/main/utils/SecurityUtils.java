package com.globalhub.main.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SecurityUtils {

    public boolean hasRole(String role) {
        return Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> Objects.equals(grantedAuthority.getAuthority(), "ROLE_" + role));
    }

}
