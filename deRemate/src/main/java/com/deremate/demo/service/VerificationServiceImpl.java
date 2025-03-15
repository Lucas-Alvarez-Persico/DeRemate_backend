package com.deremate.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.deremate.demo.entity.User;
import com.deremate.demo.exception.InvalidCodeException;
import com.deremate.demo.service.Interface.VerificationService;

@Service
public class VerificationServiceImpl implements VerificationService{
    
    private final Map<User, String> verificationCodes = new HashMap<>();

    @Override
    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(999999); // Ensures a 6-digit codeo
        return String.valueOf(code);
    }

    @Override
    public void saveVerificationCode(User user, String code) {
        verificationCodes.put(user, code);
    }

    @Override
    public User verifyCode(String username, String code) throws InvalidCodeException {
        for (Map.Entry<User, String> entry : verificationCodes.entrySet()) {
            if (entry.getKey().getUsername().equals(username) && entry.getValue().equals(code)) {
                return entry.getKey();
            }
        }
        throw new InvalidCodeException("Invalid verification code");
    }

    @Override
    public void removeVerificationCode(User user) {
        verificationCodes.remove(user);
    }

}
