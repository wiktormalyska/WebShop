package com.wiktormalyska.backend.configuration.auth;

public record JWTResponseDto(
        String login,
        String token
) {
}
