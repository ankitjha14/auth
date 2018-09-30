package com.auth.auth.controller;

import com.auth.auth.model.Otp;
import com.auth.auth.model.User;
import com.auth.auth.service.SaveUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class VerifyOtpController {

    @Autowired
    public SaveUser saveUser;

    @PostMapping("/verify")
    public User verify(@RequestBody Otp otp){
        if(verifyOtp(otp.getOtp())){

            return saveUser.saveUserDetails();
        }

        return null;
    }

    private boolean verifyOtp(String otp) {
        if(otp.equals("1234")) return true;
        return false;
    }
}
