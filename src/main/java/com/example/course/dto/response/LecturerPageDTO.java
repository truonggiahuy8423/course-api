package com.example.course.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Setter
@Getter
public class LecturerPageDTO {
    private Long lecturerId;
    private String username;
    private String email;
    private String phone;
    private Boolean gender;
    private LocalDate dob;

    public LecturerPageDTO(Long lecturerId, String username, String email, String phone, Boolean gender, LocalDate dob) {
        this.lecturerId = lecturerId;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;
    }
}