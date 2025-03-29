package com.deremate.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.deremate.demo.entity.User;
import com.deremate.demo.service.Interface.VerificationService;

@Service
public class VerificationServiceImpl implements VerificationService{
    
    private final Map<User, String> verificationCodes = new HashMap<>();
    private final Map<String, String> recoverPasswordCodes = new HashMap<>();

    @Override
    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(899999); // Ensures a 6-digit codeo
        return String.valueOf(code);
    }

    @Override
    public void saveVerificationCode(User user, String code) {
        verificationCodes.put(user, code);
    }

    @Override
    public void saveRecoveryPasswordCode(String username, String code) {
        String cleanUsername = username.trim().replaceAll("^\"|\"$", ""); // Quita comillas al inicio y final
        recoverPasswordCodes.put(cleanUsername,code);
    }
    
    @Override
    public User verifyCode(String username, String code){
        for (Map.Entry<User, String> entry : verificationCodes.entrySet()) {
            if (entry.getKey().getUsername().equals(username) && entry.getValue().equals(code)) {
                return entry.getKey();
            }
        }
        throw new RuntimeException("");
    }

    @Override
    public void removeVerificationCode(User user) {
        verificationCodes.remove(user);
    }

    @Override
    public boolean verifyCodeRecovery(String username, String code){ 
        String check = recoverPasswordCodes.get(username);
        if (check.equals(code)) {
            return true;
        }
        throw new RuntimeException("El código ingresado es incorrecto.");
    }

    @Override
    public void removeRecoveryCode(String username) {
        recoverPasswordCodes.remove(username);
    }

}
