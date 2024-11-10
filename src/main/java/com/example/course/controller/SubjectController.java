package com.example.course.controller;

//import com.example.course.dto.SubjectDTO;
import com.example.course.dto.response.AppResponse;
//import com.example.course.service.Subject.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subjects")
public class SubjectController {

//    private final SubjectService subjectService;

//    @GetMapping
//    public ResponseEntity<?> getAllSubjects() {
//        return ResponseEntity.ok(subjectService.getAllSubjects());
//    }
}
