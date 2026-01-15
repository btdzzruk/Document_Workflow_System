package com.example.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class SecurityUtils {

    public static String getCurrentUsername() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) return null;

        Object principal = authentication.getPrincipal();

        if (principal instanceof Jwt jwt) {
            // preferred_username là chuẩn của Keycloak
            return jwt.getClaimAsString("preferred_username");
        }

        return authentication.getName();
    }
}