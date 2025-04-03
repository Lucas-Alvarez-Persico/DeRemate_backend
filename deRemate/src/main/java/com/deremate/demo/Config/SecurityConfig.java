package com.deremate.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationProvider;


import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/user/newPassword").access(hasValidPurpose())
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/order/**").permitAll()
                        .requestMatchers("/delivery/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider);


        return http.build();
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> hasValidPurpose() {
        return (authentication, context) -> {
            HttpServletRequest request = context.getRequest();
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return new AuthorizationDecision(false);
            }

            String token = authHeader.substring(7); // Quita "Bearer "
            String purpose = jwtService.extractClaim(token, claims -> claims.get("purpose", String.class));
            System.out.println(token);
            System.out.println(purpose);

            return new AuthorizationDecision("RECOVER".equals(purpose));
        };
    }
}
