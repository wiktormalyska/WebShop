package com.wiktormalyska.backend.configuration.auth;

import com.wiktormalyska.backend.dto.UserResponseDto;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

public class LoginUserDetailsService implements UserDetailsService {
    private final LoginAndRegisterFacade loginAndRegisterFacade;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserResponseDto user = loginAndRegisterFacade.getUserByUsername(username);
        return getUser(user);
    }

    private User getUser(UserResponseDto dto){
        String role = dto.isAdmin() ? "ADMIN" : "USER";

        return new User(
            dto.getUsername(),
            dto.getPassword(),
            Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role))
        )
    }
}
