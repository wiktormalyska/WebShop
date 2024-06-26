package com.wiktormalyska.backend.configuration.auth;

import com.wiktormalyska.backend.dto.UserRequestDto;
import com.wiktormalyska.backend.dto.UserResponseDto;
import com.wiktormalyska.backend.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    User mapToUser(UserRequestDto dto) {
        return User.builder()
                .username(dto.username())
                .password(dto.password())
                .build();
    }

    UserResponseDto mapToUserResponseDto(User user) {
        return UserResponseDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .isAdmin(user.isAdmin())
                .build();
    }
}
