package com.ITGate.crm.repository;

import com.ITGate.crm.enums.RoleName;
import com.ITGate.crm.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleName roleName);
}
