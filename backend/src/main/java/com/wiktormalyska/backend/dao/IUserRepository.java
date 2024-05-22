package com.wiktormalyska.backend.dao;

import com.wiktormalyska.backend.model.User;

import java.util.Collection;
import java.util.Optional;

public interface IUserRepository {
    void addUser(User user);
    void removeUser(int id);
    Collection<User> getUsers();
    User getUser(int id);
    Optional<User> getUser(String username);

}
