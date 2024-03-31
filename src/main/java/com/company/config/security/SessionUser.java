package com.company.config.security;

import com.company.entity.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SessionUser {

    public Optional<AuthUser> getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof CustomUserDetails cs) {
            return Optional.of(cs.getAuthUser());
        }
        return Optional.empty();
    }
}
