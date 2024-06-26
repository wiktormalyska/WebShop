package com.wiktormalyska.backend.configuration.auth;

import com.wiktormalyska.backend.dto.UserRequestDto;
import com.wiktormalyska.backend.dto.UserResponseDto;
import com.wiktormalyska.backend.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.UserDatabase;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Log4j2
@AllArgsConstructor
@Component
public class LoginAndRegisterFacade {
    private final UserMapper userMapper;
    private final UserDatabase userRepository;

    public UserResponseDto register(UserRequestDto requestDto) {
        final User user = userMapper.mapToUser(requestDto);
        final User saved = userRepository.save(user);
        return userMapper.mapToUserResponseDto(saved);
    }

    public UserResponseDto findByUsername(String username) {
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User: " + username + " not found"));
        log.info("User found: {}", user);
        return userMapper.mapToUserResponseDto(user);
    }


    public UserResponseDto deleteUser(final String username) {
        final User deleted = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User: " + username + " not found"));
        userRepository.deleteByUsername(username);
        return userMapper.mapToUserResponseDto(deleted);
    }

    public void changeUserRole(String username, boolean isAdmin) {
        userRepository.updateIsAdminByUsername(username, isAdmin);
    }
}
