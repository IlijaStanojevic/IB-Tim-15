package com.example.IBBackend.service;

import com.example.IBBackend.dto.UserRequest;
import com.example.IBBackend.model.User;
import com.example.IBBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public Optional<User> getByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> ret = userRepository.findUserByEmail(email);
        if (ret.isPresent()) {
            return org.springframework.security.core.userdetails.User.withUsername(email).password(ret.get().getPassword()).roles(ret.get().getDecriminatorValue()).build();
        }
        throw new UsernameNotFoundException("User not found with this email: " + email);
    }

    public User save(UserRequest userRequest) {
        User u = new User();
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
//        u.setPassword(encoder.encode(userRequest.getPassword()));
        u.setName(userRequest.getName());
        u.setSurname(userRequest.getSurname());
        u.setEmail(userRequest.getEmail());
        u.setPhoneNum(userRequest.getPhoneNum());

        return userRepository.save(u);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
