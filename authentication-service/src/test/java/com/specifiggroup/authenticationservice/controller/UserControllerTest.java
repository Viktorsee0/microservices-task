package com.specifiggroup.authenticationservice.controller;

import com.specifiggroup.authenticationservice.client.KeycloakClient;
import com.specifiggroup.authenticationservice.dto.RegistrationRequestDTO;
import com.specifiggroup.authenticationservice.dto.UserRequestDTO;
import com.specifiggroup.authenticationservice.dto.UserResponseDTO;
import com.specifiggroup.authenticationservice.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(UserController.class)
class UserControllerTest {
    @MockBean
    private UserService service;
    @MockBean
    private KeycloakClient client;
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<UserRequestDTO> jsonRequestDTO;
    @Autowired
    private JacksonTester<RegistrationRequestDTO> jsonRegistrationRequestDTO;
    @Autowired
    private JacksonTester<UserResponseDTO> jsonResponseDTO;


    private static UserRequestDTO requestDTO;
    private static UserResponseDTO responseDTO;
    private static RegistrationRequestDTO registrationRequestDTO;
    private static  String token;
    @BeforeAll
    static void setUp() {
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
        responseDTO = new UserResponseDTO(
                "user_id",
                "username",
                "username@gmail.com",
                "firstname",
                "lastname"
        );
        token = """
                 "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ1eDJBa3c1eHB5VTgtMzdfbmpoOFo1UEtfZGFteEl1emhUX3ZldmdEc2RNIn0.eyJleHAiOjE2OTk3MDkyMjksImlhdCI6MTY5OTcwODkyOSwianRpIjoiODMyZjgxOGEtOGZkMi00MTg2LTgwM2EtYzQ1OTE1ODgzZTBlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NDg0L2F1dGgvcmVhbG1zL3NwZWNpZmljLWdyb3VwLXJlYWxtIiwic3ViIjoiNTMzZGZlMGItOWQwYy00ODgxLWEyZTUtYmMwMWZkZTdmOWRmIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYWRtaW4tY2xpIiwic2Vzc2lvbl9zdGF0ZSI6ImJiZjdiMDVjLWZjYzUtNDdiZi04ZTUzLTViMmM5NWExNGNjYSIsImFjciI6IjEiLCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJzaWQiOiJiYmY3YjA1Yy1mY2M1LTQ3YmYtOGU1My01YjJjOTVhMTRjY2EiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJKb2huMyBEb2UzIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiam9obl9kb2UzIiwiZ2l2ZW5fbmFtZSI6IkpvaG4zIiwiZmFtaWx5X25hbWUiOiJEb2UzIiwiZW1haWwiOiJqb2huLmRvZTNAZXhhbXBsZS5jb20ifQ.C6Ut46wwxshvNZYXjnCi7XfmIn1TMekc1WZvK8-3caeuFu2dGo0v38rWFL3nj587kRyWqjxc2VgD-Li_Un6dUPKd6YHNVmaxEEb30dmKppwAxL4WqvI-Sjt6u08Knc2FGfddOYLmkJA5DAp6XvYYVWqykAlJn_yZqHZZzDcVci8SlNadjORaCh3HrE_GjXNELe6xFIkn9XMbfgl4P_JvXXfPVBktYC2YiXN7Dm3rhOufVMGu7q3eiRYCy9du6hDEQxqmiMD1KY2UNaKl35azfwo9bkLWj59g2NNoW6UDpTRpSN3tcqcIoGyZ-Rmd2kGZ5iAMvuwHyBLsuqxYynQhXA",
                                "expires_in": 300,
                                "refresh_expires_in": 1800,
                                "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJjNDk3ODQxOS1jY2I2LTRiZGYtODI3OS1mZTZhODBkNGU5OWMifQ.eyJleHAiOjE2OTk3MTA3MjksImlhdCI6MTY5OTcwODkyOSwianRpIjoiODNhNDg1Y2ItNjZiMy00ZWJhLWJlODItNGU4ZWU5NGI4NDYwIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NDg0L2F1dGgvcmVhbG1zL3NwZWNpZmljLWdyb3VwLXJlYWxtIiwiYXVkIjoiaHR0cDovL2xvY2FsaG9zdDo4NDg0L2F1dGgvcmVhbG1zL3NwZWNpZmljLWdyb3VwLXJlYWxtIiwic3ViIjoiNTMzZGZlMGItOWQwYy00ODgxLWEyZTUtYmMwMWZkZTdmOWRmIiwidHlwIjoiUmVmcmVzaCIsImF6cCI6ImFkbWluLWNsaSIsInNlc3Npb25fc3RhdGUiOiJiYmY3YjA1Yy1mY2M1LTQ3YmYtOGU1My01YjJjOTVhMTRjY2EiLCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJzaWQiOiJiYmY3YjA1Yy1mY2M1LTQ3YmYtOGU1My01YjJjOTVhMTRjY2EifQ.GQ59pQuLewfv2al1itKE6nCR_UBu15EGQh-2WJdQIMw",
                                "token_type": "Bearer",
                                "not-before-policy": 0,
                                "session_state": "bbf7b05c-fcc5-47bf-8e53-5b2c95a14cca",
                                "scope": "profile email"
                """;
    }

    @Test
    @DisplayName("123")
    void createUser() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                        post("/api/v1/user/auth")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRegistrationRequestDTO.write(registrationRequestDTO).getJson()))
                .andReturn().getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("123")
    void getUser() throws Exception {
        // given
        given(service.getUser(any()))
                .willReturn(responseDTO);
        // when
        MockHttpServletResponse response = mvc.perform(
                        get("/api/v1/user" + "/user_name"))
                .andReturn().getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(jsonResponseDTO.write(responseDTO).getJson());
    }

    @Test
    @DisplayName("Test updating method")
    void updateUser() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                        put("/api/v1/user/" + "/user_id")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequestDTO.write(requestDTO).getJson()))
                .andReturn().getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Test removing method")
    void deleteUser() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                        delete("/api/v1/user/" + "/user_id"))
                .andReturn().getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("Test token getting method")
    void getToken() throws Exception {
        // given
        given(client.getToken(any(String.class), any(String.class)))
                .willReturn(ResponseEntity.ok(token));
        // when
        MockHttpServletResponse response = mvc.perform(
                        get("/api/v1/user/auth")
                                .param("username", "username")
                                .param("password", "password")
                )
                .andReturn().getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(token);
    }
}