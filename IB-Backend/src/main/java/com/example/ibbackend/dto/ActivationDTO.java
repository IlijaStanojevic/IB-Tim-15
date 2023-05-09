package com.example.ibbackend.dto;

public class ActivationDTO {

    private String email;
    private String activationCode;

    public ActivationDTO(String email, String activationCode) {
        this.email = email;
        this.activationCode = activationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
}
