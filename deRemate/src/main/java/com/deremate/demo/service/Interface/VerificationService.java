package com.deremate.demo.service.Interface;

import com.deremate.demo.entity.User;
import com.deremate.demo.exception.InvalidCodeException;

public interface VerificationService {

    public String generateVerificationCode();

    public void saveVerificationCode(User user, String code);

    public void saveRecoveryPasswordCode(String username, String code);

    public User verifyCode(String username, String code) throws InvalidCodeException;

    public void removeVerificationCode(User user);
    

}
