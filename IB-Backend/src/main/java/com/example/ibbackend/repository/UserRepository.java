package com.example.IBBackend.repository;

import com.example.IBBackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends MongoRepository<User, Integer> {


    public Optional<User> findUserByEmail(String email);

}
