package com.deremate.demo.service.Interface;

import com.deremate.demo.DTO.AuthenticationResponseDTO;
import com.deremate.demo.DTO.LoginRequestDTO;
import com.deremate.demo.entity.User;

import java.util.Optional;

public interface UserService {

    public String registerMail(User user);

    public AuthenticationResponseDTO register(String username, String code);

    public AuthenticationResponseDTO login(LoginRequestDTO login);

    public String  RecoverPasswordMail(String username);

    public String RecoverPassword(String username, String code);

    public Optional<User> getCurrentUser(String username, String newPassword);

}
