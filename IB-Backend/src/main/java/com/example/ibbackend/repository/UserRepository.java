package com.example.ibbackend.repository;

import com.example.ibbackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends MongoRepository<User, Integer> {

    public Optional<User> findUserByEmail(String email);

}
