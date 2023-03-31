package com.example.IBBackend;

import com.example.IBBackend.model.User;
import com.example.IBBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class IbBackendApplication  {
	@Autowired
	private UserRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(IbBackendApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		repository.save(new User(1, "AAAAAAAAAAAAAAAAAAAA@gma", "123", "Pera", "Peric"));
//		repository.save(new User(2, "aaaaaaaa@gma", "123", "Djura", "Djuric"));
//		for (User user : repository.findAll()) {
//			System.out.println(user);
//		}
//		repository.deleteById(1);
//		for (User user : repository.findAll()) {
//			System.out.println(user);
//		}
//	}
}
