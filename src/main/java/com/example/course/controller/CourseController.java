package com.example.course.controller;

import com.example.course.dto.request.CreateCourseRequest;
import com.example.course.dto.response.*;
import com.example.course.dto.response.CourseCardDTO;
import com.example.course.dto.response.CourseDTO;
import com.example.course.dto.response.GetCourseDTO;
import com.example.course.service.CourseService;
import com.example.course.util.ApiMessage;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.course.dto.request.CreateCourseRequest;
import com.example.course.dto.request.UpdateCourseRequest;
import com.example.course.dto.response.AppResponse;
import com.example.course.dto.response.CourseDTO;
import com.example.course.dto.response.CreateCourseResponse;
import com.example.course.dto.response.GetLecturersDTO;
import com.example.course.dto.response.GetRoomsDTO;
import com.example.course.dto.response.GetStudentsDTO;
import com.example.course.dto.response.GetSubjectsDTO;
import com.example.course.entity.Course;
import com.example.course.service.CourseService;
import com.example.course.util.ApiMessage;

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

                return new ResponseEntity<AppResponse<GetCoursesDTO>>(
                                new AppResponse<GetCoursesDTO>(HttpStatus.OK.value(),
                                                ApiMessage.SUCCESS,
                                                courseService.getCourses(page, pageSize, sort, sortDir)),
                                HttpStatus.OK);
        }

        @GetMapping("/get-lecturer-list")
        public ResponseEntity<AppResponse<GetLecturersDTO>> getLecturersList(
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                        @RequestParam(value = "sort", defaultValue = "1") String sort,
                        @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                return new ResponseEntity<AppResponse<GetLecturersDTO>>(
                                new AppResponse<GetLecturersDTO>(HttpStatus.OK.value(),
                                                ApiMessage.SUCCESS,
                                                courseService.getLecturers(page, pageSize, sort, sortDir)),
                                HttpStatus.OK);
        }

        @PostMapping("/cards")
        public ResponseEntity<List<CourseCardDTO>> getAllCourseCards() {
                List<CourseCardDTO> courseCards = courseService.getAllCourseCards();
                return ResponseEntity.ok(courseCards);
        }

        @GetMapping("/get-subject-list")
        public ResponseEntity<AppResponse<GetSubjectsDTO>> getSubjectList(
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                        @RequestParam(value = "sort", defaultValue = "1") String sort,
                        @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                return new ResponseEntity<AppResponse<GetSubjectsDTO>>(
                                new AppResponse<GetSubjectsDTO>(HttpStatus.OK.value(),
                                                ApiMessage.SUCCESS,
                                                courseService.getSubjects(page, pageSize, sort, sortDir)),
                                HttpStatus.OK);
        }

        @GetMapping("/get-student-list")
        public ResponseEntity<AppResponse<GetStudentsDTO>> getStudentList(
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                        @RequestParam(value = "sort", defaultValue = "1") String sort,
                        @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                return new ResponseEntity<AppResponse<GetStudentsDTO>>(
                                new AppResponse<GetStudentsDTO>(HttpStatus.OK.value(),
                                                ApiMessage.SUCCESS,
                                                courseService.getStudents(page, pageSize, sort, sortDir)),
                                HttpStatus.OK);
        }

        @GetMapping("/get-room-list")
        public ResponseEntity<AppResponse<GetRoomsDTO>> getRoomList(
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                        @RequestParam(value = "sort", defaultValue = "1") String sort,
                        @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                return new ResponseEntity<AppResponse<GetRoomsDTO>>(new AppResponse<GetRoomsDTO>(HttpStatus.OK.value(),
                                ApiMessage.SUCCESS, courseService.getRooms(page, pageSize, sort, sortDir)),
                                HttpStatus.OK);
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
                return new ResponseEntity<AppResponse<GetStudentsDTO>>(new AppResponse<GetStudentsDTO>(
                                HttpStatus.OK.value(),
                                ApiMessage.SUCCESS,
                                courseService.getStudentsByCourseId(courseId, page, pageSize, sort, sortDir)),
                                HttpStatus.OK);
        }

        @GetMapping("/get-course-details-by-id")
        public ResponseEntity<AppResponse<CourseDTO>> getCourseDetailsById(
                        @RequestParam(value = "courseId") Long courseId) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                return new ResponseEntity<AppResponse<CourseDTO>>(new AppResponse<CourseDTO>(HttpStatus.OK.value(),
                                ApiMessage.SUCCESS, courseService.getCourseByIdWithMembers(courseId)), HttpStatus.OK);
        }

        @PostMapping("/create-course")
        public ResponseEntity<AppResponse<CreateCourseResponse>> createCourse(
                        @RequestBody CreateCourseRequest createCourseRequest) {
                // Authentication
                return new ResponseEntity<AppResponse<CreateCourseResponse>>(
                                new AppResponse<CreateCourseResponse>(HttpStatus.OK.value(),
                                                ApiMessage.SUCCESS, courseService.createCourse(createCourseRequest)),
                                HttpStatus.OK);
        }

        @PutMapping("/update-course")
        public ResponseEntity<AppResponse<Course>> updateCourse(
                        @RequestParam(value = "courseId") Long courseId,
                        @RequestBody UpdateCourseRequest updateCourseRequest) {

                Course updatedCourse = courseService.updateCourse(courseId, updateCourseRequest);

                // if (updatedCourse == null) {
                // return new ResponseEntity<>(new AppResponse<Course>(
                // HttpStatus.BAD_REQUEST.value(),
                // ApiMessage.FAIL,
                // null), HttpStatus.BAD_REQUEST);
                // }

                return new ResponseEntity<>(new AppResponse<Course>(
                                HttpStatus.OK.value(),
                                ApiMessage.SUCCESS,
                                updatedCourse), HttpStatus.OK);
        }

        @GetMapping("/test-role")
        public ResponseEntity<AppResponse<String>> testRole() {
                // System.out.println("Id: " + jwt.getClaimAsString("scope"));
                // return new AppResponse<String>(HttpStatus.OK.value(), ApiMessage.SUCCESS,
                // jwt.getSubject());
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null) {
                        Jwt id = (Jwt) authentication.getPrincipal();

                        return new ResponseEntity<AppResponse<String>>(new AppResponse<String>(HttpStatus.OK.value(),
                                        ApiMessage.SUCCESS, id.getSubject()), HttpStatus.OK);
                }
                return new ResponseEntity<AppResponse<String>>(new AppResponse<String>(HttpStatus.OK.value(),
                                ApiMessage.SUCCESS, "Null"), HttpStatus.OK);
        }

}
