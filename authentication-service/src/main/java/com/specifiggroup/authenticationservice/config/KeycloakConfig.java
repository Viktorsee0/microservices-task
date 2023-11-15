package com.specifiggroup.authenticationservice.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {
    private final KeycloakProperties properties;

    @Bean
    public  Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(properties.getServerUrl())
                .realm(properties.getRealm())
                .clientId(properties.getClient().getId())
//                .clientSecret(properties.getClient().getSecret())
                .grantType(OAuth2Constants.PASSWORD)
                .username(properties.getUsername())
                .password(properties.getPassword())
                .build();
    }

    @Bean
    public UsersResource getUserResource() {
        RealmResource realmResource = keycloak().realm("specific-group-realm");
        return realmResource.users();
    }
}
