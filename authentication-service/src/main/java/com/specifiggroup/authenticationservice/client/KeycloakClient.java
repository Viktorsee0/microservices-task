package com.specifiggroup.authenticationservice.client;

import com.specifiggroup.authenticationservice.config.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KeycloakClient {
    private final KeycloakProperties properties;

    public ResponseEntity<String> getToken(String username,
                                           String password) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");
        requestBody.add("client_id", properties.getClient().getId());
        requestBody.add("client_secret", properties.getClient().getSecret());
        requestBody.add("username", username);
        requestBody.add("password", password);

        HttpEntity<MultiValueMap<String, String>> requestEntity =
                new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(properties.getToken().getEndpoint(),
                HttpMethod.POST, requestEntity, String.class);
    }
}
