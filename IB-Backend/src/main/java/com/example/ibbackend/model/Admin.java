package com.example.ibbackend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User{
    public Admin(Integer id, String email, String password, String name, String surname) {
		super(id, email, password, name, surname);
	}
}
