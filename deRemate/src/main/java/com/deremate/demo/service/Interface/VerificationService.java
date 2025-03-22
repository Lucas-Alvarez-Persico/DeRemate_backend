package com.deremate.demo.service.Interface;

import com.deremate.demo.entity.User;

public interface VerificationService {

    public String generateVerificationCode();

    public void saveVerificationCode(User user, String code);

    public void saveRecoveryPasswordCode(String username, String code);

    public User verifyCode(String username, String code);

    public void removeVerificationCode(User user);
    
    public boolean verifyCodeRecovery(String username, String code);

    public void removeRecoveryCode(String username);

}
