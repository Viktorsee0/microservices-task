package com.specifiggroup.authenticationservice.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDTO {

    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @NotBlank(message = "ConfirmPassword cannot be blank")
    private String confirmPassword;
    @NotBlank(message = "Firstname cannot be blank")
    private String firstname;
    @NotBlank(message = "Lastname cannot be blank")
    private String lastname;

    @AssertTrue(message = "Password and Confirm Password must match")
    public boolean isPasswordMatch() {
        return password != null && password.equals(confirmPassword);
    }
}
