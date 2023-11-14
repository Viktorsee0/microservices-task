package com.specifiggroup.authenticationservice.mapper;

import com.specifiggroup.authenticationservice.dto.RegistrationRequestDTO;
import com.specifiggroup.authenticationservice.dto.UserRequestDTO;
import com.specifiggroup.authenticationservice.dto.UserResponseDTO;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserMapperTest {
    @Autowired
    private UserMapper mapper;
    private RegistrationRequestDTO registrationRequestDTO;
    private UserRequestDTO requestDTO;
    private UserRepresentation userRepresentation;

    @BeforeEach
    public void setUp() {
        registrationRequestDTO = new RegistrationRequestDTO(
                "username",
                "username@gmail.com",
                "password",
                "password",
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
    void registrationDTOToRepresentation() {
        UserRepresentation result = mapper.toRepresentation(registrationRequestDTO);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(result.getUsername()).isEqualTo(registrationRequestDTO.getUsername());
        softAssertions.assertThat(result.getEmail()).isEqualTo(registrationRequestDTO.getEmail());
        softAssertions.assertThat(result.getFirstName()).isEqualTo(registrationRequestDTO.getFirstname());
        softAssertions.assertThat(result.getLastName()).isEqualTo(registrationRequestDTO.getLastname());
        softAssertions.assertAll();
    }

    @Test
    void requestDTOToRepresentation() {
        UserRepresentation result = mapper.toRepresentation(requestDTO);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(result.getUsername()).isEqualTo(requestDTO.getUsername());
        softAssertions.assertThat(result.getEmail()).isEqualTo(requestDTO.getEmail());
        softAssertions.assertThat(result.getFirstName()).isEqualTo(requestDTO.getFirstname());
        softAssertions.assertThat(result.getLastName()).isEqualTo(requestDTO.getLastname());
        softAssertions.assertAll();
    }

    @Test
    void toResponseDTO() {
        UserResponseDTO result = mapper.toResponseDTO(userRepresentation);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(result.getUsername()).isEqualTo(userRepresentation.getUsername());
        softAssertions.assertThat(result.getEmail()).isEqualTo(userRepresentation.getEmail());
        softAssertions.assertThat(result.getFirstname()).isEqualTo(userRepresentation.getFirstName());
        softAssertions.assertThat(result.getLastname()).isEqualTo(userRepresentation.getLastName());
        softAssertions.assertAll();
    }
}