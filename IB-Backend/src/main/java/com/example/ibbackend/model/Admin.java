package com.example.IBBackend.model;



//@Entity
//@DiscriminatorValue("ADMIN")
public class Admin extends User{
    public Admin(Integer id, String email, String password, String name, String surname) {
		super(id, email, password, name, surname);
	}


}
