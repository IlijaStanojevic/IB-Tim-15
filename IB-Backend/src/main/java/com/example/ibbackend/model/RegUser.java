package com.example.IBBackend.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class RegUser extends User{
    public RegUser(Integer id, String email, String password, String name, String surname) {
		super(id, email, password, name, surname);
	}
}
