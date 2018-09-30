package com.auth.auth.controller;

import com.auth.auth.model.AccessToken;
import com.auth.auth.model.User;
import com.auth.auth.service.CheckAccessToken;
import com.auth.auth.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CheckAccessTokenController {
    @Autowired
    public CheckAccessToken checkAccessToken;

    @PostMapping("/checkaccess")
    public ResponseEntity<User> regenerateAccessToken(@RequestBody AccessToken accessToken){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(checkAccessToken.checkAccessToken(accessToken.getAccessToken()));
        } catch (CustomException e) {
            if(e.getStatusCode()==401)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

            else
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
}
