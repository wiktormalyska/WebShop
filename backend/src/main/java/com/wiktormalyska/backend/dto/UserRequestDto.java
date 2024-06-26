package com.wiktormalyska.backend.dto;

import lombok.Builder;

@Builder
public record UserRequestDto(
        String username,
        String password
) {
}