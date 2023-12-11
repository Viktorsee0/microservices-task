package com.specificgroup.userservice.controller;

import com.specificgroup.userservice.dto.Credentials;
import com.specificgroup.userservice.dto.UserDto;
import com.specificgroup.userservice.entity.User;
import com.specificgroup.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@Valid @RequestBody UserDto userdto) {
        User user = userService.saveUser(userdto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<User> findUserBy(@Valid @RequestBody Credentials credentials) {
        User user = userService.findUserBy(credentials);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("all/")
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
