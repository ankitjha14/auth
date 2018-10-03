package com.auth.auth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RefreshToken {
    @Id
    @GeneratedValue
    public int id;

    @Column(unique = true)
    public String refreshToken;
    public long refreshTokenCreationTime;
    public String email;
    public String phone;

    public RefreshToken(String refreshToken, long refreshTokenCreationTime, String userId, String phone) {
        this.refreshToken = refreshToken;
        this.refreshTokenCreationTime = refreshTokenCreationTime;
        this.email = userId;
        this.phone = phone;
    }

    public RefreshToken() {
    }

}
