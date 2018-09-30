package com.auth.auth.service;

import com.auth.auth.model.User;
import com.auth.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.auth.auth.utils.AccessTokenGenerator.generateAccessToken;
import static com.auth.auth.utils.RefreshTokenGenerator.generateRefreshToken;

@Service
public class SaveUser {
    @Autowired
    private UserRepository userRepository;

    public User saveUserDetails() {

        User user = new User("8588984781", "ankit",
                generateAccessToken(), generateRefreshToken(),
                System.currentTimeMillis(), System.currentTimeMillis());

        User savedUser = userRepository.save(user);

        return savedUser;

    }

}
