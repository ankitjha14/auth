package com.auth.auth.controller;

import com.auth.auth.utils.EncodeJwtToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendOtpContorller {

    @GetMapping("/sendotp")
    public String sendOtp(){
        EncodeJwtToken jwt = new EncodeJwtToken();
        return jwt.createJWT("1","auth","c",600000);
        //return generateOtp();
    }
}
