package com.example.ibbackend.model;

import jakarta.persistence.DiscriminatorValue;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class RegUser extends User{
    public RegUser(String id, String email, String password, String phoneNum, String name, String surname) {
		super(id, email, name, surname, phoneNum, password);
	}

	@Transient
	public String getDecriminatorValue() {
		return this.getClass().getAnnotation(DiscriminatorValue.class).value();
	}

}
