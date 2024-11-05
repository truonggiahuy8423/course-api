package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entity.CourseStudent;
import com.example.course.entity.composite.CourseStudentId;

public interface CourseStudentRepository extends JpaRepository<CourseStudent, CourseStudentId> {
}
