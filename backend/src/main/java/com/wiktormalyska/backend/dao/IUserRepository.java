package com.wiktormalyska.backend.dao;

import com.wiktormalyska.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
