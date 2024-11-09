package com.example.course.repository;

import com.example.course.dto.response.LecturerInCreateCourseDTO;
import com.example.course.dto.response.StudentInCreateCourseDTO;
import com.example.course.entity.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Override
    List<Student> findAll();
    @Query("SELECT new com.example.course.dto.response.StudentInCreateCourseDTO(" +
            "s.studentId, u.userId, u.username, u.email, u.gender, u.dob, u.lastAccess, u.avatar) " +
            "FROM Student s " +
            "LEFT JOIN s.user u ")
    List<StudentInCreateCourseDTO> findStudents(Pageable pageable);
}
