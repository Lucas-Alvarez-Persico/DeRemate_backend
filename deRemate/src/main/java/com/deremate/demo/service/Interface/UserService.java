package com.deremate.demo.service.Interface;

import com.deremate.demo.DTO.AuthenticationResponseDTO;
import com.deremate.demo.DTO.LoginRequestDTO;
import com.deremate.demo.entity.User;
import com.deremate.demo.exception.InvalidCodeException;

import java.util.Optional;

public interface UserService {

    public void registerMail(User user);

    public AuthenticationResponseDTO register(String username, String code) throws InvalidCodeException;

    public AuthenticationResponseDTO login(LoginRequestDTO login);

    public void  RecoverPasswordMail(String username);

    public String RecoverPassword(String username, String code) throws InvalidCodeException;

    public Optional<User> getCurrentUser(String username, String newPassword) throws RuntimeException;

}
