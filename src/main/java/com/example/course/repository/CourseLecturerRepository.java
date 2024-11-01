package com.example.course.repository;

import com.example.course.entity.CourseLecturer;
import com.example.course.entity.composite.CourseLecturerId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseLecturerRepository extends JpaRepository<CourseLecturer, CourseLecturerId> {
}
