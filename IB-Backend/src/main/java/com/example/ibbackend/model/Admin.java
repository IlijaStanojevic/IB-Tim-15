package com.example.IBBackend.model;


import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@DiscriminatorValue("ADMIN")
@DiscriminatorColumn(name = "TYPE")
public class Admin extends User{
	public Admin(String id, String email, String password, String phoneNum, String name, String surname) {
		super(id, email, name, surname, phoneNum, password);
	}
}
