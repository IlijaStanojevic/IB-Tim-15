package com.example.IBBackend;

import com.example.IBBackend.model.User;
import com.example.IBBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class IbBackendApplication  {
//	@Autowired
//	private UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(IbBackendApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		userService.save(new User(1, "AAAAAAAAAAAAAAAAAAAA@gma", "123", "Pera", "Peric"));
//		userService.save(new User(2, "aaaaaaaa@gma", "123", "Djura", "Djuric"));
//		for (User user : userService.findAll()) {
//			System.out.println(user);
//		}
//
//		System.out.println(userService.loadUserByUsername("aaaaaaaa@gma"));
//
//	}
}
