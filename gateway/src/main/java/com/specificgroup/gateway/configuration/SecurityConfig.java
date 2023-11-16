package com.specificgroup.gateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.WebFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomWebFilter webFilter;

    @Bean
    public SecurityWebFilterChain configureResourceServer(ServerHttpSecurity httpSecurity) {
        return httpSecurity.authorizeExchange(
                        exchange -> exchange.pathMatchers(
                                        "/actuator/health/**",
                                        "/*/*/user/auth/**").permitAll()
                                .anyExchange().authenticated())
                .addFilterBefore(webFilter, SecurityWebFiltersOrder.AUTHORIZATION)
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }
}
