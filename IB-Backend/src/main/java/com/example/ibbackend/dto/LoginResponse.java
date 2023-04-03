package com.example.ibbackend.dto;

import java.util.Date;

public class LoginResponse {
    private String accessToken;


    private String userId;
    private Date expiresIn;

    public LoginResponse() {
        this.accessToken = null;
        this.expiresIn = null;
    }

    public LoginResponse(String accessToken, Date expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Date expiresIn) {
        this.expiresIn = expiresIn;
    }


}
