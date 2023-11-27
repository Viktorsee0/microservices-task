package com.specificgroup.authenticationservice.service;

import com.specificgroup.authenticationservice.dto.RegistrationRequestDTO;
import com.specificgroup.authenticationservice.dto.UserRequestDTO;
import com.specificgroup.authenticationservice.dto.UserResponseDTO;
import com.specificgroup.authenticationservice.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;
    @Mock
    private UsersResource usersResource;
    @Mock
    private UserResource userResource;
    @Autowired
    private UserMapper mapper;

    private RegistrationRequestDTO registrationRequestDTO;
    private UserResponseDTO responseDTO;
    private UserRequestDTO requestDTO;
    private UserRepresentation userRepresentation;

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(usersResource, mapper);

        registrationRequestDTO = new RegistrationRequestDTO(
                "username",
                "username@gmail.com",
                "password",
                "password",
                "firstname",
                "lastname"
        );

        responseDTO = new UserResponseDTO(
                "user_id",
                "username",
                "username@gmail.com",
                "firstname",
                "lastname"
        );

        requestDTO = new UserRequestDTO(
                "username",
                "username@gmail.com",
                "firstname",
                "lastname"
        );

        userRepresentation = new UserRepresentation();
        userRepresentation.setId("user_id");
        userRepresentation.setUsername("username");
        userRepresentation.setEmail("username@gmail.com");
        userRepresentation.setFirstName("firstname");
        userRepresentation.setLastName("lastname");
    }

    @Test
    @DisplayName("Test creating user")
    void create() {
        userService.createUser(registrationRequestDTO);
        verify(usersResource).create(any());
    }

    @Test
    @DisplayName("Test getting user by username")
    void getUserByUsername() {
        given(usersResource.search(any(String.class)))
                .willReturn(List.of(userRepresentation));
        UserResponseDTO result = userService.getUser("username");
        assertThat(result).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("Test update user")
    void updateUser(){
        given(usersResource.get(any(String.class)))
                .willReturn(userResource);
        userService.updateUser("user_id", requestDTO);
        verify(userResource).update(any());
    }

    @Test
    @DisplayName("Test update user")
    void deleteUser(){
        given(usersResource.get(any(String.class)))
                .willReturn(userResource);
        userService.deleteUser("user_id");
        verify(userResource).remove();
    }
}