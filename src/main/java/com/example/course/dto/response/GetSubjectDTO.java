package com.example.course.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class GetSubjectDTO {
    List<SubjectInCreateCourseDTO> subjects;
    Integer total;
}
