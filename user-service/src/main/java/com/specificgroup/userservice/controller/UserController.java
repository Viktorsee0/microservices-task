package com.specificgroup.userservice.controller;

import com.netflix.appinfo.ApplicationInfoManager;
import com.specificgroup.userservice.dto.Credentials;
import com.specificgroup.userservice.dto.UserDto;
import com.specificgroup.userservice.entity.User;
import com.specificgroup.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<User> saveUser(@Valid @RequestBody UserDto userdto) {
        User user = userService.saveUser(userdto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> findUserBy(@Valid @RequestBody Credentials credentials) {
        User user = userService.findUserBy(credentials);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    String getTestMessageFromServiceByUser() {
        return "User message from server ";
    }

    @GetMapping("/moderator-access")
    @ResponseStatus(HttpStatus.OK)
    String getTestMessageFromServiceByManager() {
        return "Moderator message from server ";
    }
}
