package com.company.config.security;

import com.company.dao.AuthUserDao;
import com.company.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserDao authUserDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AuthUser authUser = authUserDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with '%s' email not found"));
        return new CustomUserDetails(authUser);
    }
}
