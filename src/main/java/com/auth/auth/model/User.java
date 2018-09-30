package com.auth.auth.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private String id;
    private String name;
    private String accessToken;
    private String refreshToken;
    private long refreshTokenCreationTime;

    public User(String id, String name, String accessToken, String refreshToken, long refreshTokenCreationTime, long accessTokenCreationTime) {
        this.id = id;
        this.name = name;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.refreshTokenCreationTime = refreshTokenCreationTime;
        this.accessTokenCreationTime = accessTokenCreationTime;
    }

    private long accessTokenCreationTime;

    public long getRefreshTokenCreationTime() {
        return refreshTokenCreationTime;
    }

    public void setRefreshTokenCreationTime(long refreshTokenCreationTime) {
        this.refreshTokenCreationTime = refreshTokenCreationTime;
    }

    public long getAccessTokenCreationTime() {
        return accessTokenCreationTime;
    }

    public void setAccessTokenCreationTime(long accessTokenCreationTime) {
        this.accessTokenCreationTime = accessTokenCreationTime;
    }

    public User(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}