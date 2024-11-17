package com.example.course.repository;

import com.example.course.dto.response.LecturerPageDTO;
import com.example.course.entity.Lecturer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    @Query("SELECT new com.example.course.dto.response.LecturerPageDTO(" +
                "l.lecturerId," +
                "u.username," +
                "u.email," +
                "u.phone," +
                "u.gender," +
                "u.dob) " +
            "FROM Lecturer l " +
            "LEFT JOIN l.user u ")
    List<LecturerPageDTO> getAllLecturerInfo(Pageable pageable);
}