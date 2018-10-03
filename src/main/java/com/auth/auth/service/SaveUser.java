package com.auth.auth.service;

import com.auth.auth.model.GoogleUser;
import com.auth.auth.model.RefreshToken;
import com.auth.auth.model.User;
import com.auth.auth.repository.GoogleUserRepository;
import com.auth.auth.repository.RefreshTokenRepository;
import com.auth.auth.repository.UserRepository;
import com.auth.auth.utils.EncodeJwtToken;
import com.auth.auth.utils.RefreshTokenGenerator;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.auth.auth.utils.AccessTokenGenerator.generateAccessToken;
import static com.auth.auth.utils.RefreshTokenGenerator.generateRefreshToken;

@Service
public class SaveUser {
    @Autowired
    private GoogleUserRepository userRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public GoogleUser save(GoogleUser user) {
        GoogleUser savedUser = user;
        try {
            savedUser = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            return savedUser;
        }

        return savedUser;

    }

    public String generateJwtToken(GoogleUser user) {
        EncodeJwtToken jwt = new EncodeJwtToken();
        Map<String, String> userData = new HashMap<String, String>();
        userData.put("firstname",user.getFirstname());
        userData.put("lastname",user.getLastname());
        userData.put("email",user.getEmail());
        userData.put("phone",user.getPhone());
        userData.put("picture",user.getPicture());
        String token = jwt.createJWT("1", "auth", "c", 300000, userData);

        return token;

    }

    public String generateRefreshToken(GoogleUser user) {
        String token = RefreshTokenGenerator.generateRefreshToken();
        long ts = System.currentTimeMillis();
        RefreshToken refreshToken = new RefreshToken(token, ts, user.getEmail(), user.getPhone());
        refreshTokenRepository.save(refreshToken);
        try {
            refreshTokenRepository.save(refreshToken);
        } catch (DataIntegrityViolationException e) {
            return generateRefreshToken(user);
        }

        return token;

    }

}
