package com.example.course.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course.dto.response.StudentResponse;
import com.example.course.entity.Student;
import com.example.course.mapper.StudentMapper;
import com.example.course.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentRepository studentRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> getStudentById(Long studentId) {
        return studentRepository.findById(studentId);
    }

    public List<StudentResponse> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentResponse> studentResponses = new ArrayList<>();
        for (Student student : students) {
            StudentResponse studentResponse = new StudentResponse();
            studentMapper.mapStudentToStudentResponse(studentResponse, student);
            studentResponses.add(studentResponse);
        }
        return studentResponses;
    }

    public String test() {
        return "j cung đc";
    }

    public Student updateStudent(Long studentId, Student studentDetails) {
        return studentRepository.findById(studentId).map(student -> {
            student.setName(studentDetails.getName());
            student.setGender(studentDetails.getGender());
            student.setDob(studentDetails.getDob());
            student.setEmail(studentDetails.getEmail());
            return studentRepository.save(student);
        }).orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
    }

    public void deleteAStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }
}
