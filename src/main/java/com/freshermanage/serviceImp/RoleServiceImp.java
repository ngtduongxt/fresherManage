package com.freshermanage.serviceImp;

import com.freshermanage.model.ERole;
import com.freshermanage.model.Roles;
import com.freshermanage.repository.RoleRepository;
import com.freshermanage.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<Roles> findByRoleName(ERole roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
