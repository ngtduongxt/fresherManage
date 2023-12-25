package com.freshermanage.service;

import com.freshermanage.model.ERole;
import com.freshermanage.model.Roles;

import java.util.Optional;

public interface RoleService {
    Optional<Roles> findByRoleName(ERole roleName);
}
