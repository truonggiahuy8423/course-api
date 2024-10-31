package com.example.course.repository;

import com.example.course.entity.PermissionUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionUserRepository  extends JpaRepository<PermissionUser, Long> {
}
