package com.example.course.controller;

import com.example.course.dto.response.CourseCardDTO;
import com.example.course.dto.response.CourseDTO;
import com.example.course.dto.response.GetCourseDTO;
import com.example.course.dto.request.CreateCourseRequest;
import com.example.course.dto.response.*;
import com.example.course.service.CourseService;
import com.example.course.util.ApiMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/get-course-list")
    public ResponseEntity<AppResponse<GetCoursesDTO>> getCourses(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sort", defaultValue = "1") String sort,
            @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(page);

        return new ResponseEntity<AppResponse<GetCoursesDTO>>(new AppResponse<GetCoursesDTO>(HttpStatus.OK.value(),
                ApiMessage.SUCCESS, courseService.getCourses(page, pageSize, sort, sortDir)), HttpStatus.OK);
    }


    @GetMapping("/get-lecturer-list")
    public ResponseEntity<AppResponse<GetLecturersDTO>> getLecturersList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sort", defaultValue = "1") String sort,
            @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<AppResponse<GetLecturersDTO>>(new AppResponse<GetLecturersDTO>(HttpStatus.OK.value(),
                ApiMessage.SUCCESS, courseService.getLecturers(page, pageSize, sort, sortDir)), HttpStatus.OK);
    }

    @GetMapping("/get-subject-list")
    public ResponseEntity<AppResponse<GetSubjectsDTO>> getSubjectList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sort", defaultValue = "1") String sort,
            @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<AppResponse<GetSubjectsDTO>>(new AppResponse<GetSubjectsDTO>(HttpStatus.OK.value(),
                ApiMessage.SUCCESS, courseService.getSubjects(page, pageSize, sort, sortDir)), HttpStatus.OK);
    }

    @GetMapping("/get-student-list")
    public ResponseEntity<AppResponse<GetStudentsDTO>> getStudentList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sort", defaultValue = "1") String sort,
            @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<AppResponse<GetStudentsDTO>>(new AppResponse<GetStudentsDTO>(HttpStatus.OK.value(),
                ApiMessage.SUCCESS, courseService.getStudents(page, pageSize, sort, sortDir)), HttpStatus.OK);
    }

    @GetMapping("/get-room-list")
    public ResponseEntity<AppResponse<GetRoomsDTO>> getRoomList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sort", defaultValue = "1") String sort,
            @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<AppResponse<GetRoomsDTO>>(new AppResponse<GetRoomsDTO>(HttpStatus.OK.value(),
                ApiMessage.SUCCESS, courseService.getRooms(page, pageSize, sort, sortDir)), HttpStatus.OK);
    }

    @GetMapping("/get-course-by-id")
    public ResponseEntity<AppResponse<CourseDTO>> getCourseById(
            @RequestParam(value = "courseId") Long courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<AppResponse<CourseDTO>>(new AppResponse<CourseDTO>(HttpStatus.OK.value(),
                ApiMessage.SUCCESS, courseService.getCourseById(courseId)), HttpStatus.OK);
    }

    @GetMapping("/get-students-by-course-id")
    public ResponseEntity<AppResponse<GetStudentsDTO>> getStudentsByCourseId(
            @RequestParam(value = "courseId") Long courseId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sort", defaultValue = "1") String sort,
            @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<AppResponse<GetStudentsDTO>>(new AppResponse<GetStudentsDTO>(HttpStatus.OK.value(),
                ApiMessage.SUCCESS, courseService.getStudentsByCourseId(courseId, page, pageSize, sort, sortDir)), HttpStatus.OK);
    }

    @GetMapping("/get-course-details-by-id")
    public ResponseEntity<AppResponse<CourseDTO>> getCourseDetailsById(
            @RequestParam(value = "courseId") Long courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<AppResponse<CourseDTO>>(new AppResponse<CourseDTO>(HttpStatus.OK.value(),
                ApiMessage.SUCCESS, courseService.getCourseByIdWithMembers(courseId)), HttpStatus.OK);
    }

    @PostMapping("/create-course")
    public ResponseEntity<AppResponse<CreateCourseResponse>> createCourse(@RequestBody CreateCourseRequest createCourseRequest) {
        // Authentication
        return new ResponseEntity<AppResponse<CreateCourseResponse>>(new AppResponse<CreateCourseResponse>(HttpStatus.OK.value(),
                ApiMessage.SUCCESS, courseService.createCourse(createCourseRequest)), HttpStatus.OK);
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


    @PostMapping("/cards")
    public ResponseEntity<List<CourseCardDTO>> getAllCourseCards() {
        List<CourseCardDTO> courseCards = courseService.getAllCourseCards();
        return ResponseEntity.ok(courseCards);
    }

}
