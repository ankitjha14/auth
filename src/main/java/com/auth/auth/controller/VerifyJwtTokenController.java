package com.auth.auth.controller;

import com.auth.auth.model.GoogleUser;
import com.auth.auth.model.JwtToken;
import com.auth.auth.service.SaveUser;
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
import java.util.Map;

@RestController
public class VerifyJwtTokenController {

    @Autowired
    public SaveUser saveUser;


    @PostMapping("/token")
    public ResponseEntity<LinkedHashMap>  verify(@RequestBody JwtToken jwtToken){
        DecodeJwtToken decodeJwtToken = new DecodeJwtToken();
        Claims claims = null;
        try{

            claims = decodeJwtToken.parseJWT(jwtToken.getJwtToken());
        }catch (ExpiredJwtException e){
            LinkedHashMap<String,String> error = new LinkedHashMap<>();
            error.put("message","Expired or Wrong JWT Token");
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }catch (SignatureException e){
            LinkedHashMap<String,String> error = new LinkedHashMap<>();
            error.put("message","Expired or Wrong JWT Token");
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(claims.get("user",LinkedHashMap.class), HttpStatus.OK);
    }

}
