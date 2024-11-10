package com.example.course.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.course.dto.request.StudentRequest;
import com.example.course.dto.response.StudentResponse;
import com.example.course.entity.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    public void mapStudentRequestToStudent(@MappingTarget Student student, StudentRequest request);

    public void mapStudentToStudentResponse(@MappingTarget StudentResponse response, Student student);
}
