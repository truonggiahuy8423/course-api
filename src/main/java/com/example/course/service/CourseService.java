package com.example.course.service;

import com.example.course.dto.response.CourseDTO;
import com.example.course.dto.response.GetCourseDTO;
import com.example.course.dto.response.LecturerDTO;
import com.example.course.entity.Lecturer;
import com.example.course.repository.CourseRepository;
import com.example.course.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    public GetCourseDTO getCourses(Integer page, Integer pageSize, String sort, String sortDir) {
        // Tạo Pageable từ các tham số được truyền vào
        String sortAttr = getSortAttribute(sort); // Hàm lấy thuộc tính sắp xếp tương ứng từ số
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, sortAttr);

        List<CourseDTO> res = courseRepository.getCourses(pageable);
        res = res.stream()
                .peek(courseDTO -> courseDTO.setLecturers(getLecturersByCourseId(courseDTO.getCourseId())))
                .toList();
        return new GetCourseDTO(res, courseRepository.findAll().size());
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


    public List<LecturerDTO> getLecturersByCourseId(Long courseId) {
        return lecturerRepository.findByCourseId(courseId);
    }

    public List<Object[]> test(Integer page_index, Integer limit) {
        Pageable pageable = PageRequest.of(page_index - 1, limit); // Trang đầu tiên, 5 kết quả
        return courseRepository.test(pageable);
    }
}
