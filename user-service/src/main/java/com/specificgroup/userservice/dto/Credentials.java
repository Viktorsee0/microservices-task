package com.specificgroup.userservice.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public class Credentials {
    @NotBlank
    private String username;

    private Optional<String> password;

    public String getUsername() {
        return username;
    }
    public Optional<String> getPassword() {
        return password;
    }
}
