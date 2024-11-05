package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entity.CourseSchedule;

public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, Long> {
}
