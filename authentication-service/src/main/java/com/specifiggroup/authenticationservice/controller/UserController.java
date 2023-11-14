package com.specifiggroup.authenticationservice.controller;

import com.specifiggroup.authenticationservice.client.KeycloakClient;
import com.specifiggroup.authenticationservice.service.UserService;
import com.specifiggroup.authenticationservice.dto.RegistrationRequestDTO;
import com.specifiggroup.authenticationservice.dto.UserRequestDTO;
import com.specifiggroup.authenticationservice.dto.UserResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService userService;
    private final KeycloakClient client;

    @PostMapping("/auth")
    public ResponseEntity<Void> createUser(@RequestBody @Valid
                                               RegistrationRequestDTO userDTO) {
        log.info("Registration a new user new {}", userDTO.getUsername());
        userService.createUser(userDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/{userName}")
    public ResponseEntity<UserResponseDTO> getUser(@NotBlank(message = "Username cannot be blank")
                                            @PathVariable("userName") String username) {
        log.info("Getting an user by username: {}", username);
        return ResponseEntity.ok(userService.getUser(username));
    }

    @PutMapping(path = "/{userId}")
    public  ResponseEntity<Void> updateUser(@NotBlank(message = "UserId cannot be blank")
                      @PathVariable("userId") String userId,
                      @RequestBody UserRequestDTO userDTO) {
        log.info("Updating an user with id: {}", userId);
        userService.updateUser(userId, userDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{userId}")
    public  ResponseEntity<Void> deleteUser(@NotBlank(message = "UserId cannot be blank")
                      @PathVariable("userId") String userId) {
        log.info("Removing an user with id: {}", userId);
        userService.deleteUser(userId);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping("/auth")
    public ResponseEntity<String> getToken(@NotBlank(message = "Username cannot be blank")
                                    @RequestParam(name = "username") String username,
                                    @NotBlank(message = "Password cannot be blank")
                                    @RequestParam(name = "password") String password) {
        log.info("Generating an token for user: {}", username);
        return client.getToken(username, password);
    }
}
