package com.rahul.security.service;

import com.rahul.security.model.User;
import com.rahul.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username).
                orElseThrow(() -> new RuntimeException("Username not found"));
    }
}
