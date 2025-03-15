package com.deremate.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.deremate.demo.entity.User;
import com.deremate.demo.exception.InvalidCodeException;
import com.deremate.demo.repository.UserRepository;
import com.deremate.demo.service.Interface.UserService;
import com.deremate.demo.service.Interface.VerificationService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerMail(User user) {

        String code = verificationService.generateVerificationCode();
        verificationService.saveVerificationCode(user, code);
        mailService.sendConfirmationMail(user.getUsername(), code);

        return;

    }

    @Override
    public void register(String username, String code) throws InvalidCodeException {
        
        User user = verificationService.verifyCode(username, code);
        verificationService.removeVerificationCode(user);
        userRepository.save(user);

        return;
    
    }


    @Override
    public void login(User user) {
        // TODO Auto-generated method stub
        return;
    }

}
