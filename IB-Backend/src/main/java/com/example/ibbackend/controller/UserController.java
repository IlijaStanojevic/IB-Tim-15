package com.example.ibbackend.controller;

import com.example.ibbackend.dto.JwtAuthenticationRequest;
import com.example.ibbackend.dto.LoginResponse;
import com.example.ibbackend.dto.UserRequest;
import com.example.ibbackend.model.User;
import com.example.ibbackend.security.jwt.JwtTokenUtil;
import com.example.ibbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private JwtTokenUtil tokenUtil;


    @Autowired
    private UserService userService;
    @PostMapping("/api/user/login")
    public ResponseEntity createToken (@RequestBody JwtAuthenticationRequest authenticationRequest){

        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(), authenticationRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = tokenUtil.generateToken(userDetails.getUsername());
            Date expiresIn = tokenUtil.getExpirationDateFromToken(jwt);

            LoginResponse userTokenState = new LoginResponse(jwt, expiresIn);
            String userId;
            if (userDetails instanceof User) {
                userId = ((User) userDetails).getId();
                userTokenState.setUserId(userId);
            }

            return ResponseEntity.ok(userTokenState);
        }
        catch(Exception ignored) {
            return new ResponseEntity("Wrong username or password!", HttpStatus.BAD_REQUEST);
        }
    }
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/api/user/signup")
    public ResponseEntity addUser(@RequestBody UserRequest userRequest) {
        Optional<User> existUser = this.userService.getByEmail(userRequest.getEmail());


        if (existUser.isPresent()) {
            return new ResponseEntity<>("Email already in use!", HttpStatus.BAD_REQUEST);
        }

        User user = this.userService.save(userRequest);

        return new ResponseEntity<>("Successful sign up!", HttpStatus.CREATED);
    }
}
