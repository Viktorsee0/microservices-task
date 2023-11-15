package com.specifiggroup.authenticationservice.service;

import com.specifiggroup.authenticationservice.dto.RegistrationRequestDTO;
import com.specifiggroup.authenticationservice.dto.UserRequestDTO;
import com.specifiggroup.authenticationservice.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    void createUser(RegistrationRequestDTO userDTO);
    UserResponseDTO getUser(String userName);
    List<UserResponseDTO> getUsers();
    void updateUser(String userId, UserRequestDTO userDTO);
    void deleteUser(String userId);
}
