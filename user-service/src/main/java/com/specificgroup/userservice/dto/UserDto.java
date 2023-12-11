package com.specificgroup.userservice.dto;

import jakarta.validation.constraints.NotBlank;

public class UserDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String firstname;
    private String lastname;
    private String email;

    public UserDto(String username, String password, String firstname, String lastname, String email) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }
}
