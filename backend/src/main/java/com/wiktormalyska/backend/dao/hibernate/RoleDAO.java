package com.wiktormalyska.backend.dao.hibernate;

import com.wiktormalyska.backend.dao.IRoleRepository;
import com.wiktormalyska.backend.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDAO implements IRoleRepository {


    @Override
    public Role findByName(String name) {
        return switch (name) {
            case "USER" -> new Role("USER");
            case "ADMIN" -> new Role("ADMIN");
            default -> null;
        };
    }
}
