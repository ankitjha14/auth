package com.auth.auth.service;

import com.auth.auth.model.User;
import com.auth.auth.repository.UserRepository;
import com.auth.auth.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.auth.auth.utils.AccessTokenGenerator.generateAccessToken;

@Service
public class RegenerateAccessToken {
    @Autowired
    public UserRepository userRepository;

    @Transactional
    public User regenerateAccessToken(String refreshToken) throws CustomException {
        User mayBeUser;
        try {
            mayBeUser = userRepository.findByRefreshToken(refreshToken).get(0);

            if (System.currentTimeMillis() - mayBeUser.getRefreshTokenCreationTime() >= 160000) {
                throw new CustomException(401);
            }

        } catch (Exception e) {
            throw new CustomException(401);
        }

        if (System.currentTimeMillis() - mayBeUser.getAccessTokenCreationTime() >= 80000) {
            long ts = System.currentTimeMillis();
            userRepository.updateAccessTokenByRefreshToken(generateAccessToken(), ts, refreshToken);
        }


        return mayBeUser;

    }
}
