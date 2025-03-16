package com.deremate.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deremate.demo.DTO.AuthenticationResponseDTO;
import com.deremate.demo.DTO.LoginRequestDTO;
import com.deremate.demo.DTO.RegisterRequestDTO;
import com.deremate.demo.entity.User;
import com.deremate.demo.exception.InvalidCodeException;
import com.deremate.demo.service.Interface.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/register/mail")
    public ResponseEntity<String> registerMail(@RequestBody User user ) {
        userService.registerMail(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/register")
    public AuthenticationResponseDTO register(@RequestBody RegisterRequestDTO registerRequestDTO) throws InvalidCodeException {
        return userService.register(registerRequestDTO.getUsername(), registerRequestDTO.getCode());
        
    }

    @PostMapping("/login")
    public AuthenticationResponseDTO login(@RequestBody LoginRequestDTO login) {
        return userService.login(login);
    }

}
