package com.specificgroup.authenticationservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {
    private String serverUrl;
    private String realm;
    private Client client;
    private String username;
    private String password;
    private Token token;

    @Getter
    @Setter
    public static class Client {
        private String id;
    }

    @Getter
    @Setter
    public static class Token {
        private String endpoint;
        private String client;
        private String secret;
    }
}
