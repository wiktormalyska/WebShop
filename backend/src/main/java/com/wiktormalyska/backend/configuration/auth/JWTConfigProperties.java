package com.wiktormalyska.backend.configuration.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "auth.jwt")
public record JWTConfigProperties(
        String secret,
        long expirationTime
) {
}
