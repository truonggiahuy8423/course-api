package com.example.course.entity;

import com.example.course.entity.composite.HistoryId;
import com.example.course.entity.composite.PermissionUserId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "history_view")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HistoryView {
    @EmbeddedId
    private HistoryId id;
    // Relation "One"

    // Relation "Many"
    @JsonIgnore
    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private Course course;

    @JsonIgnore
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime updatedDate;
}