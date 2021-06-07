package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Role;
import com.synrgybootcamp.project.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRoleName(RoleName roleName);
}
