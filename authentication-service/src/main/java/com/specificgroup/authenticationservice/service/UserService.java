package com.specificgroup.authenticationservice.service;

import com.specificgroup.authenticationservice.dto.RegistrationRequestDTO;
import com.specificgroup.authenticationservice.dto.UserRequestDTO;
import com.specificgroup.authenticationservice.dto.UserResponseDTO;

import java.util.List;
import java.util.UUID;

/**
 * This service includes the basic logic for evaluating the system rating
 */
public interface UserService {

    /**
     * Registration of user
     *
     * @param userDTO is a user representation
     */
    void createUser(RegistrationRequestDTO userDTO);

    /**
     * Receives a user by username
     *
     * @param username is a username of user
     * @return a user representation for response
     */
    UserResponseDTO getUser(String username);

    /**
     * Receives a user by username
     *
     * @param id is an id of user
     * @return a user representation for response
     */
    UserResponseDTO getUserById(UUID id);

    /**
     * Receives a list of all users
     *
     * @return a list of all users
     */
    List<UserResponseDTO> getUsers();

    /**
     * Update user data
     *
     * @param userId is an id of user
     * @param userDTO is a new user data to be loaded
     */
    void updateUser(String userId, UserRequestDTO userDTO);

    /**
     * Removes user by id
     * @param userId is an id of user
     */
    void deleteUser(String userId);
}
