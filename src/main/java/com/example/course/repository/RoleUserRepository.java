package com.example.course.repository;

import com.example.course.entity.Role;
import com.example.course.entity.RoleUser;
import com.example.course.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleUserRepository  extends JpaRepository<RoleUser, Long> {
    boolean existsByUserAndRole(User user, Role role);
}
