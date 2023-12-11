package com.specificgroup.userservice.service;

import com.specificgroup.userservice.dto.Credentials;
import com.specificgroup.userservice.dto.UserDto;
import com.specificgroup.userservice.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(UserDto userDto);

    User findUserBy(Credentials credentials);


    List<User> findAllUsers();
}
