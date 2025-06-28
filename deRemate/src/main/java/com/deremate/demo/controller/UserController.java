package com.deremate.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import com.deremate.demo.DTO.LoginRequestDTO;
import com.deremate.demo.DTO.PushTokenDTO;
import com.deremate.demo.DTO.RegisterRequestDTO;
import com.deremate.demo.DTO.UserDTO;
import com.deremate.demo.ErrorResponse.ErrorResponse;
import com.deremate.demo.entity.PushToken;
import com.deremate.demo.entity.User;
import com.deremate.demo.repository.PushTokenRepository;
import com.deremate.demo.service.Interface.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private PushTokenRepository pushTokenRepository;

    @PostMapping("/register/mail")
    public ResponseEntity<?> registerMail(@RequestBody User user ) {
        try{
            userService.registerMail(user);
            return ResponseEntity.ok("Se ha enviado un mensaje al correo.");
        }catch(RuntimeException e){
            ErrorResponse errorResponse = new ErrorResponse("Error al enviar correo: "+e.getMessage());
            return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO registerRequestDTO){
        try{
            return ResponseEntity.ok(userService.register(registerRequestDTO.getUsername(), registerRequestDTO.getCode()));
        }catch(RuntimeException e){
            ErrorResponse errorResponse = new ErrorResponse("Error al registrar usuario: "+e.getMessage());
            return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO login){
        try{
            return ResponseEntity.ok(userService.login(login));
        }catch(RuntimeException e){
            ErrorResponse errorResponse = new ErrorResponse("Error al iniciar sesion: "+e.getMessage());
            return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/recover/mail")
    public ResponseEntity<?> recoverMail(@RequestBody String username){
        try{
            return ResponseEntity.ok(userService.RecoverPasswordMail(username));
        }catch(RuntimeException e){
            ErrorResponse errorResponse = new ErrorResponse("Error al enviar correo: "+e.getMessage());
            return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/recover")
    public ResponseEntity<?> recover(@RequestBody RegisterRequestDTO recoverRequest){
        try{
            return ResponseEntity.ok(userService.RecoverPassword(recoverRequest.getUsername(), recoverRequest.getCode()));
        }catch(RuntimeException e){
            ErrorResponse errorResponse = new ErrorResponse("Error al recuperar usuario: "+e.getMessage());
            return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping("/newPassword")
    public Optional<User> newPassword(@RequestBody LoginRequestDTO login){
        return userService.getCurrentUser(login.getUsername(), login.getPassword());
    
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile() {
    
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO profile = userService.getProfile(user.getUsername());
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/push-token")
    public ResponseEntity<?> savePushToken(@RequestBody PushTokenDTO dto) {
        System.out.println("hola" + dto);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Evitá duplicados
        boolean exists = pushTokenRepository.findByToken(dto.getToken())
            .stream()
            .anyMatch(token -> token.getUser().getId().equals(user.getId()));

        if (!exists) {
            PushToken pushToken = PushToken.builder()
                .token(dto.getToken())
                .user(user)
                .build();
            pushTokenRepository.save(pushToken);
        }

        return ResponseEntity.ok().build();
    }

}
