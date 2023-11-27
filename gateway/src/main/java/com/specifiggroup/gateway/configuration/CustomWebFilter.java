package com.specifiggroup.gateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class CustomWebFilter implements WebFilter {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();

        String path = request.getURI().getPath();
        if (!path.startsWith("/*/*/todo")){
            return chain.filter(exchange);
        }

        String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
        String jwtToken = authorizationHeader != null && authorizationHeader.startsWith("Bearer ")
                ? authorizationHeader.substring(7) : null;

        if (jwtToken != null) {
            NimbusJwtDecoder decoder = NimbusJwtDecoder
                    .withJwkSetUri(jwkSetUri)
                    .build();
            Jwt jwt = decoder.decode(jwtToken);
            String userId = jwt.getClaim("sub");

            URI modifiedUri = UriComponentsBuilder.fromUri(request.getURI())
                    .queryParam("user_id", userId)
                    .build()
                    .toUri();

            ServerHttpRequest modifiedRequest = request.mutate()
                    .uri(modifiedUri)
                    .build();

            ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(modifiedRequest)
                    .build();

            return chain.filter(modifiedExchange);
        }

        return chain.filter(exchange);
    }
}