package com.deremate.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.deremate.demo.DTO.AuthenticationResponseDTO;
import com.deremate.demo.entity.User;
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
    public String registerMail(User user) {
        if(!userRepository.existsByUsername(user.getUsername())){
            String code = verificationService.generateVerificationCode();
            verificationService.saveVerificationCode(user, code);
            String subject = "Código de verificación del correo";
            String body = "Tu código de verificación es: " + code
                    + ". Porfavor, ingrese el código en la aplicación para validar la cuenta.";
            System.out.println("ENVIANDO MAIL..............");
            try {
                mailService.sendConfirmationMail(user.getUsername(), subject, body);
            } catch (Exception e) {
                throw new RuntimeException("ERROR AL ENVIAR MAIL");
            } 
            return "se envio el correo.";
        }else{
            throw new RuntimeException("El usuario ya existe.");
        }
    }

    @Override
    public AuthenticationResponseDTO register(String username, String code){
        try{
            User user = verificationService.verifyCode(username, code);
            verificationService.removeVerificationCode(user);
            return authenticationService.register(user);

    }catch(RuntimeException e){
        throw new RuntimeException("El código ingresado es incorrecto.");
    }
}

    @Override
    public AuthenticationResponseDTO login(LoginRequestDTO login) {
        return authenticationService.authenticate(login);
    }


    @Override
    public String RecoverPasswordMail(String username) {
        String cleanUsername = username.trim().replaceAll("^\"|\"$", ""); // Quita comillas al inicio y final
        System.out.println(cleanUsername);
        if(userRepository.existsByUsername(cleanUsername)){
            String code = verificationService.generateVerificationCode();
            verificationService.saveRecoveryPasswordCode(username, code);
            String subject = "Código de recupero de contraseña";
            String body = "Tu código de verificación es: " + code
                    + ". Porfavor, ingrese el código en la aplicación para poder cambiar su contraseña.";

            mailService.sendConfirmationMail(username, subject, body);
            return "se envio el correo.";
        }else{
            throw new RuntimeException("El usuario no existe.");
        }

    }

    @Override
    public String RecoverPassword(String username, String code) {
        try{
            verificationService.verifyCodeRecovery(username, code);
            verificationService.removeRecoveryCode(username);
            return authenticationService.generateRecoverToken(username);
        }catch(RuntimeException e){
            throw new RuntimeException("El código ingresado es incorrecto.");

        }
    }

    public Optional<User> getCurrentUser(String username, String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(username);
        if (authentication == null || authentication.getPrincipal() instanceof String) {
            User user = userRepository.findByUsername(username) 
                    .orElseThrow(() -> new RuntimeException("User not found"));
            authenticationService.changePassword(newPassword, user);
            return Optional.of(user);

        }
        return Optional.empty();
    }


}