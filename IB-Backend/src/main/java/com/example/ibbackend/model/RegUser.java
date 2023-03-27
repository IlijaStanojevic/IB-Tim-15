package com.example.ibbackend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("USER")
public class RegUser extends User{
    public RegUser(Integer id, String email, String password, String name, String surname) {
		super(id, email, password, name, surname);
	}
}
