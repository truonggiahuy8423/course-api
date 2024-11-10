package com.example.course.entity;

import com.example.course.entity.enums.UserGender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "student")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Student {
    @Id
    @Column(name = "student_id", columnDefinition = "BIGINT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @Column(name = "name", columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserGender gender;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(255)")
    private String email;

    @OneToOne(mappedBy = "student")
    private User user;

    // Relation "One"
    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<Submit> submits;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<CourseStudent> courses;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<Attendance> attendances;
}
