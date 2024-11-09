package com.example.course.repository;

import com.example.course.entity.CourseStudent;
import com.example.course.entity.composite.CourseStudentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseStudentRepository extends JpaRepository<CourseStudent, CourseStudentId> {
//    List<CourseStudent> getCourseStudentListByCourseId();
}
