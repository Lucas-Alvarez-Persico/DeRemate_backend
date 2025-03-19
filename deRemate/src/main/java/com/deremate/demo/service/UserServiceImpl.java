package com.deremate.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.deremate.demo.DTO.AuthenticationResponseDTO;
import com.deremate.demo.entity.User;
import com.deremate.demo.exception.InvalidCodeException;
import com.deremate.demo.service.Interface.UserService;
import com.deremate.demo.service.Interface.VerificationService;
import com.deremate.demo.DTO.LoginRequestDTO;

import com.deremate.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerMail(User user) {

        String code = verificationService.generateVerificationCode();
        verificationService.saveVerificationCode(user, code);
        String subject = "Código de verificación del correo";
        String body = "Tu código de verificación es: " + code
                + ". Porfavor, ingrese el código en la aplicación para validar la cuenta.";

        mailService.sendConfirmationMail(user.getUsername(), subject, body);
        return;
    }

    @Override
    public AuthenticationResponseDTO register(String username, String code) throws InvalidCodeException {

        User user = verificationService.verifyCode(username, code);
        verificationService.removeVerificationCode(user);
        return authenticationService.register(user);

    }

    @Override
    public AuthenticationResponseDTO login(LoginRequestDTO login) {
        return authenticationService.authenticate(login);
    }

    // SEGUIR DESPUES RECOVER PASSWORD BY MAIL:

    @Override
    public void RecoverPasswordMail(String username) {
        String code = verificationService.generateVerificationCode();
        verificationService.saveRecoveryPasswordCode(username, code);
        String subject = "Código de recupero de contraseña";
        String body = "Tu código de verificación es: " + code
                + ". Porfavor, ingrese el código en la aplicación para poder cambiar su contraseña.";

        mailService.sendConfirmationMail(username, subject, body);
        return;
    }

    @Override
    public String RecoverPassword(String username, String code) throws InvalidCodeException {
        if (verificationService.verifyCodeRecovery(username, code)) {
            verificationService.removeRecoveryCode(username);
            return authenticationService.generateRecoverToken(username);
        }
        return "Incorrecto";
    }

    public Optional<User> getCurrentUser(String username, String newPassword) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || authentication.getPrincipal() instanceof String) {
        User user = userRepository.findByUsername(username) 
                .orElseThrow(() -> new RuntimeException("User not found"));
        authenticationService.changePassword(newPassword, user);
    }
        return null;
}


}