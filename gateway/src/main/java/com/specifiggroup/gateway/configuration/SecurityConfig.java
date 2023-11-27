package com.specifiggroup.gateway.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
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
