package com.auth.auth.controller;

import com.auth.auth.utils.EncodeJwtToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SendOtpContorller {

    @GetMapping("/sendotp")
    public String sendOtp(){
        Map<String, String> userData = new HashMap<String, String>();
        userData.put("name","ankit");
        userData.put("email","ptani");
        EncodeJwtToken jwt = new EncodeJwtToken();
        return jwt.createJWT("1","auth","c",600000,userData);
        //return generateOtp();
    }
}
