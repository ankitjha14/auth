package com.auth.auth.controller.testcontrollers;

import com.auth.auth.model.JwtToken;
import com.auth.auth.service.JwtTokenService;
import com.auth.auth.service.RegisterUser;
import com.auth.auth.utils.DecodeJwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
public class VerifyJwtTokenController {

    @Autowired
    public JwtTokenService jwtTokenService;


    @PostMapping("/token")
    public ResponseEntity<LinkedHashMap>  verify(@RequestBody JwtToken jwtToken){
        LinkedHashMap user = null;
        try{
           user = jwtTokenService.verifyJWT(jwtToken.getJwtToken());
           if(user==null){
               return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
           }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
