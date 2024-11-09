package com.example.course.repository;

import com.example.course.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Long> {
    boolean existsByRoleName(String roleName);
    Role findByRoleName(String roleName);
}
