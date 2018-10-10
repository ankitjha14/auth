package com.auth.auth.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LogoutController {


    @GetMapping("/logout")
    public ResponseEntity<String> fetchData(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        HttpHeaders headers = new HttpHeaders();
        if (cookies != null) {

            for (int i = 0; i < cookies.length; i++) {
                headers.add("Set-Cookie", cookies[i].getName() + "=" + null);
            }
        }

        return new ResponseEntity<String>("Successfully logged out!!",headers, HttpStatus.OK);
    }

}
