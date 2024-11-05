package com.example.course.service;

import com.example.course.dto.response.*;
import com.example.course.repository.CourseRepository;
import com.example.course.repository.LecturerRepository;
import com.example.course.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LecturerRepository lecturerRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    public GetCoursesDTO getCourses(Integer page, Integer pageSize, String sort, String sortDir) {
        // Tạo Pageable từ các tham số được truyền vào
        String sortAttr = getSortAttribute(sort); // Hàm lấy thuộc tính sắp xếp tương ứng từ số
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, sortAttr);

        List<CourseDTO> res = courseRepository.getCourses(pageable);
        res = res.stream()
                .peek(courseDTO -> courseDTO.setLecturers(getLecturersByCourseId(courseDTO.getCourseId())))
                .toList();
        return new GetCoursesDTO(res, courseRepository.findAll().size());
    }

    public GetLecturersDTO getLecturers(Integer page, Integer pageSize, String sort, String sortDir) {
        String sortAttr = getSortAttributeLecturers(sort); // Hàm lấy thuộc tính sắp xếp tương ứng từ số
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, sortAttr);

        List<LecturerInCreateCourseDTO> lecturers = lecturerRepository.findLecturers(pageable);
        Integer total = lecturerRepository.findAll().size();
        return new GetLecturersDTO(lecturers, total);
    }

    public GetSubjectDTO getSubjects(Integer page, Integer pageSize, String sort, String sortDir) {
        String sortAttr = getSortAttributeSubjects(sort); // Hàm lấy thuộc tính sắp xếp tương ứng từ số
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, sortAttr);

        List<SubjectInCreateCourseDTO> subjects = subjectRepository.findSubjects(pageable);
        Integer total = subjectRepository.findAll().size();
        return new GetSubjectDTO(subjects, total);
    }


    // Hàm lấy thuộc tính sắp xếp
    private String getSortAttribute(String sort) {
        Map<Integer, String> sortMapper = new HashMap<>();
        sortMapper.put(1, "c.courseId");
        sortMapper.put(2, "c.subject.subjectName");
        sortMapper.put(3, "c.startDate");
        sortMapper.put(4, "c.endDate");
        sortMapper.put(5, "COUNT(s)");

        return sortMapper.get(Integer.valueOf(sort));
    }

    private String getSortAttributeLecturers(String sort) {
        Map<Integer, String> sortMapper = new HashMap<>();
        sortMapper.put(1, "lec.lecturerId");
        sortMapper.put(2, "u.username");
//        sortMapper.put(3, "c.startDate");
//        sortMapper.put(4, "c.endDate");
//        sortMapper.put(5, "COUNT(s)");

        return sortMapper.get(Integer.valueOf(sort));
    }

    private String getSortAttributeSubjects(String sort) {
        Map<Integer, String> sortMapper = new HashMap<>();
        sortMapper.put(1, "s.subjectId");
        sortMapper.put(2, "s.subjectName");
//        sortMapper.put(3, "c.startDate");
//        sortMapper.put(4, "c.endDate");
//        sortMapper.put(5, "COUNT(s)");

        return sortMapper.get(Integer.valueOf(sort));
    }


    public List<LecturerDTO> getLecturersByCourseId(Long courseId) {
        return lecturerRepository.findByCourseId(courseId);
    }

    public List<Object[]> test(Integer page_index, Integer limit) {
        Pageable pageable = PageRequest.of(page_index - 1, limit); // Trang đầu tiên, 5 kết quả
        return courseRepository.test(pageable);
    }
}
