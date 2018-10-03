package com.auth.auth.controller;

import com.auth.auth.model.GoogleUser;
import com.auth.auth.model.RefToken;
import com.auth.auth.model.RefreshToken;
import com.auth.auth.repository.GoogleUserRepository;
import com.auth.auth.repository.RefreshTokenRepository;
import com.auth.auth.service.SaveUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetNewJwtTokenController {

    @Autowired
    public RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public GoogleUserRepository googleUserRepository;

    @Autowired
    public SaveUser saveUser;


    @PostMapping("/reftoken")
    public ResponseEntity<String> verify(@RequestBody RefToken refToken) {
        RefreshToken refreshToken = refreshTokenRepository.findAllByRefreshToken(refToken.getRefToken());

        if (refreshToken == null||
                System.currentTimeMillis() - refreshToken.refreshTokenCreationTime >= 7.884e+9) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        GoogleUser user = null;

        if (refreshToken.email != null) {
            user = googleUserRepository.findByEmail(refreshToken.email);
        } else {
            user = googleUserRepository.findByPhone(refreshToken.phone);
        }

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = saveUser.generateJwtToken(user);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
