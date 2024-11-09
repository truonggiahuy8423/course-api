package com.example.course.repository;

import com.example.course.entity.Attendance;
import com.example.course.entity.composite.AttendanceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, AttendanceId> {
}
