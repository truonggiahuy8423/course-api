package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
