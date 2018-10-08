package com.auth.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;

@Component
public class DecodeJwtToken {
    private static String apiKey;

    @Value("${jwt.apiKey}")
    public void setDirectory(String value) {
        this.apiKey = value;

    }

    //Sample method to validate and read the JWT
    public Claims parseJWT(String jwt) {

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(apiKey))
                .parseClaimsJws(jwt).getBody();

        return claims;
    }
}
