package com.example.ibbackend.repository;

import com.example.ibbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    @Query(value = "select * from Users where email = ?1", nativeQuery = true)
    Optional<User> findByEmail(String email);

}
