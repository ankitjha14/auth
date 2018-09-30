package com.auth.auth.controller;

import com.auth.auth.model.RefreshToken;
import com.auth.auth.model.User;
import com.auth.auth.service.RegenerateAccessToken;
import com.auth.auth.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccessTokenController {
    @Autowired
    public RegenerateAccessToken regenerateAccessToken;

    @PostMapping("/newaccess")
    public ResponseEntity<User> regenerateAccessToken(@RequestBody RefreshToken refreshToken){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(regenerateAccessToken.regenerateAccessToken(refreshToken.getRefreshToken()));
        } catch (CustomException e) {
           if(e.getStatusCode()==401)
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

           else
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
}
