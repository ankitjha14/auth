package com.auth.auth.service;

import com.auth.auth.model.RefreshToken;
import com.auth.auth.model.User;
import com.auth.auth.repository.RefreshTokenRepository;
import com.auth.auth.repository.UserRepository;
import com.auth.auth.utils.DecodeJwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class JwtTokenService {

    @Autowired
    public DecodeJwtToken decodeJwtToken;

    @Autowired
    public RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public RegisterUser registerUser;

    @Value("${refToken.ExpiryTime}")
    private long expiryTime;


    public LinkedHashMap verifyJWT(String jwtToken) {
        Claims claims = null;
        try {

            claims = decodeJwtToken.parseJWT(jwtToken);
        } catch (ExpiredJwtException e) {
            return null;
        } catch (SignatureException e) {
            return null;
        } catch (Exception e) {
            return null;
        }

        return claims.get("user", LinkedHashMap.class);
    }


    public String regenerateJwt(String refToken) {
        RefreshToken refreshToken = refreshTokenRepository.findAllByRefreshToken(refToken);

        if (refreshToken == null ||
                System.currentTimeMillis() - refreshToken.refreshTokenCreationTime >= expiryTime) {   //7.884e+9
            return null;
        }


        User user = null;

        if (refreshToken.email != null) {
            user = userRepository.findByEmail(refreshToken.email);
        } else {
            user = userRepository.findByPhone(refreshToken.phone);
        }

        if (user == null) {
            return null;
        }

        String token = registerUser.generateJwtToken(user);
        return token;
    }

}
