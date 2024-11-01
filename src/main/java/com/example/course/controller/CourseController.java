package com.example.course.controller;

import com.example.course.dto.response.CourseDTO;
import com.example.course.dto.response.GetCourseDTO;
import com.example.course.service.CourseService;
import com.example.course.dto.response.AppResponse;
import com.example.course.util.ApiMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/get-courses")
    public ResponseEntity<AppResponse<GetCourseDTO>> getCourses(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sort", defaultValue = "1") String sort,
            @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(page);

        return new ResponseEntity<AppResponse<GetCourseDTO>>(new AppResponse<GetCourseDTO>(HttpStatus.OK.value(),
                ApiMessage.SUCCESS, courseService.getCourses(page, pageSize, sort, sortDir)), HttpStatus.OK);
    }


    @GetMapping("/test-role")
    public ResponseEntity<AppResponse<String>> testRole() {
//        System.out.println("Id: " + jwt.getClaimAsString("scope"));
//        return new AppResponse<String>(HttpStatus.OK.value(), ApiMessage.SUCCESS, jwt.getSubject());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Jwt id = (Jwt) authentication.getPrincipal();
            return new ResponseEntity<AppResponse<String>>(new AppResponse<String>(HttpStatus.OK.value(),
                    ApiMessage.SUCCESS, id.getSubject()), HttpStatus.OK);
        }
        return new ResponseEntity<AppResponse<String>>(new AppResponse<String>(HttpStatus.OK.value(),
                ApiMessage.SUCCESS, "Null"), HttpStatus.OK);    }



}
