package com.example.course.repository;

import com.example.course.entity.CourseScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseScheduleStatusRepository extends JpaRepository<CourseScheduleStatus, Long> {
}
