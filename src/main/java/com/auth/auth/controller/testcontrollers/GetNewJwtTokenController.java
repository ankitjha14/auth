package com.auth.auth.controller.testcontrollers;

import com.auth.auth.model.RefToken;
import com.auth.auth.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetNewJwtTokenController {

    @Autowired
    public JwtTokenService jwtToken;


    @PostMapping("/reftoken")
    public ResponseEntity<String> verify(@RequestBody RefToken refToken) {
        String token = null;
        try{
            jwtToken.regenerateJwt(refToken.getRefToken());
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
