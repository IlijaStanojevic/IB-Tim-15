package com.example.ibbackend.model;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;


@Document(collection = "users")
@DiscriminatorValue("USER")
@DiscriminatorColumn(name = "TYPE")
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String email;

    private String name;

    private String surname;
    private String phoneNum;
    private boolean isEnabled;

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Enumerated(EnumType.STRING)
    private UserRole role;
    public enum UserRole{
        ROLE_ADMIN,
        ROLE_USER
    }
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public User(String id, String email, String name, String surname, String phoneNum, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.phoneNum = phoneNum;
        this.password = password;
    }

    private String password;


    public User(){
        this.id = null;
        this.email = "";
        this.password = "";
        this.name = "";
        this.phoneNum = "";
        this.surname = "";
    }

    /**
     * @return Integer return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return String return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return String return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Transient
    public String getDecriminatorValue() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
//        throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
//        throw new UnsupportedOperationException("Unimplemented method 'isAccountNonExpired'");
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
//        throw new UnsupportedOperationException("Unimplemented method 'isAccountNonLocked'");
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
//        throw new UnsupportedOperationException("Unimplemented method 'isCredentialsNonExpired'");
    }

    @Override
    public boolean isEnabled() {
//        throw new UnsupportedOperationException("Unimplemented method 'isEnabled'");
        return isEnabled;
    }

}
