package com.example.ibbackend.security.jwt;

import com.example.ibbackend.model.User;
import com.example.ibbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository allUsers;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> ret = allUsers.findByEmail(email);
        if (ret.isPresent()) {
            return org.springframework.security.core.userdetails.User.withUsername(email).password(ret.get().getPassword()).roles(ret.get().getDecriminatorValue()).build();
        }
        throw new UsernameNotFoundException("User not found with this username: " + email);
    }


}
