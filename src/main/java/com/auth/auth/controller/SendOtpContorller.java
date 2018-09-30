package com.auth.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.auth.auth.utils.OtpGenerator.generateOtp;

@RestController
public class SendOtpContorller {

    @GetMapping("/sendotp")
    public String sendOtp(){
        return generateOtp();
    }
}
