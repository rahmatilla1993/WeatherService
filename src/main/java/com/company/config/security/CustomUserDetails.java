package com.company.config.security;

import com.company.entity.AuthUser;
import com.company.enums.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {

    private final AuthUser authUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName = authUser.getRole().name();
        return Collections.singletonList(
                new SimpleGrantedAuthority(roleName)
        );
    }

    @Override
    public String getPassword() {
        return authUser.getPassword();
    }

    @Override
    public String getUsername() {
        return authUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return authUser.getStatus().equals(Status.ACTIVE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return authUser.getStatus().equals(Status.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return authUser.getStatus().equals(Status.ACTIVE);
    }

    @Override
    public boolean isEnabled() {
        return authUser.getStatus().equals(Status.ACTIVE);
    }
}
