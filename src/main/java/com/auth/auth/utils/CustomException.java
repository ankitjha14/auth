package com.auth.auth.utils;

public class CustomException extends Exception{
    private int statusCode;

    public CustomException(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
