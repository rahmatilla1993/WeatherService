package com.company.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .userDetailsService(customUserDetailsService)
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/api/auth/**", "/css/*", "/js/*")
                            .permitAll()
                            .anyRequest()
                            .fullyAuthenticated();
                })
                .formLogin(form -> {
                    form.usernameParameter("email")
                            .passwordParameter("pswd")
                            .loginPage("/api/auth/login")
                            .defaultSuccessUrl("/", true)
                            .loginProcessingUrl("/login/process")
                            .failureHandler(authenticationFailureHandler);
                })
                .rememberMe(rem -> {
                    rem.rememberMeParameter("rem-me")
                            .tokenValiditySeconds(10 * 60)
                            .key("secretkey");
                })
                .logout(conf -> {
                    conf.logoutUrl("api/auth/logout")
                            .deleteCookies("JSESSIONID")
                            .clearAuthentication(true)
                            .logoutRequestMatcher(new AntPathRequestMatcher("/api/auth/logout", "POST"));
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
