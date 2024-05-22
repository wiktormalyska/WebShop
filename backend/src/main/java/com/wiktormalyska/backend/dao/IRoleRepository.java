package com.wiktormalyska.backend.dao;

import com.wiktormalyska.backend.model.Role;

public interface IRoleRepository {
    Role findByName(String name);
}
