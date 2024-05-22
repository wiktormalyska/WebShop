package com.wiktormalyska.backend.services;

import com.wiktormalyska.backend.dto.UserDto;
import com.wiktormalyska.backend.model.User;

import java.util.Collection;
import java.util.Optional;

public interface IUserService {
    Optional<User> findByUsername(String username);
    String registerUser(User user);
    Collection<UserDto> getUsers();
    UserDto getUser(int id);
    String addUser(User user);
    String removeUser(User user);
}
