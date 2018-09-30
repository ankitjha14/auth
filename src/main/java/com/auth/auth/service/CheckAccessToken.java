package com.auth.auth.service;

import com.auth.auth.model.User;
import com.auth.auth.repository.UserRepository;
import com.auth.auth.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CheckAccessToken {
    @Autowired
    public UserRepository userRepository;

    @Transactional
    public User checkAccessToken(String accessToken) throws CustomException {
        User mayBeUser;

        try {
            mayBeUser = userRepository.findByAccessToken(accessToken).get(0);
            if (System.currentTimeMillis() - mayBeUser.getAccessTokenCreationTime() >= 80000) {
                throw new CustomException(401);
            }

        } catch (Exception e) {
            throw new CustomException(401);
        }


        return mayBeUser;
    }
}
