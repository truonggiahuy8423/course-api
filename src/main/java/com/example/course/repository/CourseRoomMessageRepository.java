package com.example.course.repository;

import com.example.course.entity.CourseRoomMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRoomMessageRepository extends JpaRepository<CourseRoomMessage, Long> {
}
