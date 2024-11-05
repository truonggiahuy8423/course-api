package com.example.course.repository;

import com.example.course.dto.response.LecturerDTO;
import com.example.course.dto.response.LecturerInCreateCourseDTO;
import com.example.course.entity.Lecturer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    @Query("SELECT new com.example.course.dto.response.LecturerDTO(" +
            "lec.lecturerId, u.username, u.email, u.lastAccess, u.gender, u.avatar) " +
            "FROM Course c " +
            "LEFT JOIN c.lecturers l " +
            "LEFT JOIN l.lecturer lec " +
            "LEFT JOIN lec.user u " +
            "WHERE c.courseId = :courseId ")
    List<LecturerDTO> findByCourseId(Long courseId);

    @Query("SELECT new com.example.course.dto.response.LecturerInCreateCourseDTO(" +
            "lec.lecturerId, u.userId, u.username, u.email, u.gender, u.dob, u.lastAccess, u.avatar) " +
            "FROM Lecturer lec " +
            "LEFT JOIN lec.user u ")
    List<LecturerInCreateCourseDTO> findLecturers(Pageable pageable);

    @Override
    List<Lecturer> findAll();
}
