package com.example.course.controller;

import com.example.course.dto.response.AppResponse;
import com.example.course.dto.response.GetLecturerPageDTO;
import com.example.course.dto.response.LecturerPageDTO;
import com.example.course.entity.Lecturer;
import com.example.course.service.LecturerService;
import com.example.course.util.ApiMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LecturerController {
    @Autowired
    private LecturerService lecturerService;

    @GetMapping("/get-all-lecturer")
    public ResponseEntity<AppResponse<GetLecturerPageDTO>> getAllLecturers(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sort", defaultValue = "1") String sort,
            @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir
    ) {
        return new ResponseEntity<AppResponse<GetLecturerPageDTO>>(
                new AppResponse<GetLecturerPageDTO>(
                        HttpStatus.OK.value(),
                        ApiMessage.SUCCESS,
                        lecturerService.getAllLecturers(page, pageSize, sort, sortDir)
                ),
                HttpStatus.OK
            );
    }
}