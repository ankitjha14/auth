package com.auth.auth.controller;

import com.auth.auth.model.GoogleUser;
import com.auth.auth.model.Otp;
import com.auth.auth.service.SaveUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerifyOtpController {

    @Autowired
    public SaveUser saveUser;

    @PostMapping("/verify")
    public ResponseEntity<String>  verify(@RequestBody Otp otp){
        if(verifyOtp(otp.getOtp())){

            GoogleUser user = new GoogleUser(otp.firstname,otp.lastname,otp.phone);
            GoogleUser savedUser = saveUser.save(user);
            if(savedUser!=null){
                if(saveUser.generateRefreshToken(savedUser)!=null){
                    return new ResponseEntity<>(saveUser.generateJwtToken(user), HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<>("try again", HttpStatus.UNAUTHORIZED);
    }

    private boolean verifyOtp(String otp) {
        if(otp.equals("1234")) return true;
        return false;
    }
}
