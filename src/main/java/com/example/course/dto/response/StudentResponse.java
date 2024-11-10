package com.example.course.dto.response;

import java.sql.Date;
import java.time.LocalDate;

import com.example.course.entity.enums.UserGender;

import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentResponse {
    private Long studentId;

    private String name;

    private UserGender gender;

    private LocalDate dob;

    private String email;

}
