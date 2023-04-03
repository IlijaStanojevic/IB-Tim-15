package com.example.ibbackend.dto;

public class UserRequest {
    private String email;
    private String password;
    private String name;
    private String surname;
    private String phoneNum;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public UserRequest(String email, String password, String name, String surname, String phoneNum) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phoneNum = phoneNum;
    }

    public UserRequest(){}
}
