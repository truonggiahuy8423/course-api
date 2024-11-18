package com.example.course.repository;

import com.example.course.dto.response.StudentInCreateCourseDTO;
import com.example.course.entity.CourseStudent;
import com.example.course.entity.composite.CourseStudentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseStudentRepository extends JpaRepository<CourseStudent, CourseStudentId> {
//    List<CourseStudent> getCourseStudentListByCourseId();

    @Query("SELECT new com.example.course.dto.response.StudentInCreateCourseDTO(" +
            "s.studentId, u.userId, u.username, u.email, u.gender, u.dob, u.lastAccess, u.avatar) " +
            "FROM CourseStudent cs " +
            "LEFT JOIN cs.student s " +
            "LEFT JOIN s.user u WHERE cs.course.courseId = :courseId")
    List<StudentInCreateCourseDTO> findByCourseId(Long courseId, Pageable pageable);

    @Query("SELECT COUNT(*) " +
            "FROM CourseStudent cs " +
            "WHERE cs.id.courseId = :courseId")
    Integer countStudentsByCourseId(Long courseId);

}
