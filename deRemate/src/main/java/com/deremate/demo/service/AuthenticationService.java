package com.deremate.demo.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deremate.demo.repository.UserRepository;

import com.deremate.demo.DTO.AuthenticationResponseDTO;
import com.deremate.demo.DTO.LoginRequestDTO;
import com.deremate.demo.Config.JwtService;
import com.deremate.demo.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponseDTO register(User user) {
        String encodepass = user.getPassword();
        encodepass = passwordEncoder.encode(encodepass);
        user.setPassword(encodepass);

        repository.save(user);

        var jwtToken = jwtService.generateAuthToken(user);
        return AuthenticationResponseDTO.builder()
                .accessToken(jwtToken)
                .role(user.getRole())
                .build();
    }

    public AuthenticationResponseDTO authenticate(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateAuthToken(user);
        return AuthenticationResponseDTO.builder()
                .accessToken(jwtToken)
                .role(user.getRole())
                .build();
    }

    public String generateRecoverToken(String username) {
        var user = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return jwtService.generateRecoverToken(user);
    }

    public void changePassword(String newPassword, User user) {
        newPassword = passwordEncoder.encode(newPassword);
        user.setPassword(newPassword);

        repository.save(user);
    }
}