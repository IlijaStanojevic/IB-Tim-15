package com.example.ibbackend.service;

import com.example.ibbackend.dto.UserRequest;
import com.example.ibbackend.model.User;
import com.example.ibbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.*;
import java.util.stream.Collectors;

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
            return org.springframework.security.core.userdetails.User
                    .withUsername(email)
                    .password(ret.get().getPassword())
                    .authorities(ret.get().getRole().name())
                    .build();
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
        u.setRole(User.UserRole.ROLE_USER);
        u.setEnabled(false);
        var rng = new Random();
        int code = rng.nextInt(900000) + 100000;
        u.setActivationCode(String.valueOf(code));
        return userRepository.save(u);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
    private static Collection<? extends GrantedAuthority> getAuthorities(List<User.UserRole> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }

    public boolean activateUser(String email, String activationCode){
        User ret = userRepository.findUserByEmail(email).orElse(null);
        if(Objects.equals(ret.getActivationCode(), activationCode)){
            ret.setEnabled(true);
            userRepository.save(ret);
            return true;
        }else{
            return false;
        }

    }
}
