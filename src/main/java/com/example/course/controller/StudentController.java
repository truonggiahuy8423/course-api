package com.example.course.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.course.dto.request.AccessControlRequest;
import com.example.course.dto.response.AccessControlResponse;
import com.example.course.dto.response.AppResponse;
import com.example.course.dto.response.StudentResponse;
import com.example.course.service.StudentService;
import com.example.course.util.ApiMessage;

@Controller
@RequestMapping(value = "/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/all")
    public ResponseEntity<AppResponse<List<StudentResponse>>> getAccessControl() {
        List<StudentResponse> studentResponses = studentService.getAllStudents();
        return new ResponseEntity<>(new AppResponse<>(HttpStatus.OK.value(), ApiMessage.SUCCESS, studentResponses),
                HttpStatus.OK);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteAStudent(studentId);
        return ResponseEntity.noContent().build(); // Returns 204 No Content if successful
    }

    @GetMapping("/testt")
    public ResponseEntity<AppResponse<String>> testtt() {
        String studentResponses = studentService.test();
        return new ResponseEntity<>(new AppResponse<>(HttpStatus.OK.value(), ApiMessage.SUCCESS, studentResponses),
                HttpStatus.OK);
    }
}
