package com.specifiggroup.authenticationservice.service;

import com.specifiggroup.authenticationservice.dto.RegistrationRequestDTO;
import com.specifiggroup.authenticationservice.dto.UserRequestDTO;
import com.specifiggroup.authenticationservice.dto.UserResponseDTO;

public interface UserService {

    void createUser(RegistrationRequestDTO userDTO);
    UserResponseDTO getUser(String userName);
    void updateUser(String userId, UserRequestDTO userDTO);
    void deleteUser(String userId);
}
