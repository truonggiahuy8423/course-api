package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entity.Permission;
import com.example.course.entity.Role;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    boolean existsByPermissionName(String permissionName);
}
