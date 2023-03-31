package com.example.IBBackend.model;


import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Admin extends User{
	public Admin(Integer id, String email, String name, String surname, String phoneNumber, String password) {
		super(id, email, name, surname, phoneNumber, password);
	}
}
