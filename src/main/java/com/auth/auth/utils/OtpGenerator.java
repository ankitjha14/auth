package com.auth.auth.utils;

import java.util.Random;

public class OtpGenerator {
    public static String generateOtp(){
        Random random = new Random();
        String otp = String.format("%06d", random.nextInt(10000));
        return otp;
    }
}
