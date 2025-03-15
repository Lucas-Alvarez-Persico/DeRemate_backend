package com.deremate.demo.service.Interface;

import com.deremate.demo.entity.User;
import com.deremate.demo.exception.InvalidCodeException;

public interface UserService {

    public void registerMail(User user);

    public void register(String username, String code) throws InvalidCodeException;

    public void login(User user);

}
