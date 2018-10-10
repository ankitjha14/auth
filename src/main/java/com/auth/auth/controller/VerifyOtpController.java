package com.auth.auth.controller;

import com.auth.auth.model.Otp;
import com.auth.auth.model.User;
import com.auth.auth.service.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@RestController
public class VerifyOtpController {

    @Autowired
    public RegisterUser registerUser;


    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody Otp otp, HttpServletResponse res) {
        if (verifyOtp(otp.getOtp())) {

            User user = new User(otp.firstname, otp.lastname, otp.phone);
            User savedUser = registerUser.save(user);
            if (savedUser != null) {
                String refToken = registerUser.generateRefreshToken(savedUser);
                if (refToken != null) {
                    String jwtToken = registerUser.generateJwtToken(user);
                    res.addCookie(getCookie(refToken, "refToken"));
                    res.addCookie(getCookie(jwtToken, "jwtToken"));

                    return new ResponseEntity<String>("Successfully logged in!!", HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<>("try again", HttpStatus.UNAUTHORIZED);
    }

    private Cookie getCookie(String value, String name) {
        Cookie refTokenCookie = new Cookie(name, value);
        refTokenCookie.setHttpOnly(true);
        refTokenCookie.setPath("/");
        return refTokenCookie;
    }

    private boolean verifyOtp(String otp) {
        if (otp.equals("1234")) return true;
        return false;
    }
}
