package com.example.course.dto.request;

import java.sql.Date;

import com.example.course.entity.enums.UserGender;

import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;

public class StudentRequest {
    private String name;

    private UserGender gender;

    private Date dob;

    private String email;
}
