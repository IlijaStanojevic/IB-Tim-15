package com.example.IBBackend.model;


import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Admin extends User{
	public Admin(String id, String email, String password, String phoneNum, String name, String surname) {
		super(id, email, name, surname, phoneNum, password);
	}
}
