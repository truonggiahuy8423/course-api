package com.example.course.service;

import com.example.course.dto.response.CourseCardDTO;
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

import java.time.temporal.ChronoUnit;
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
    public List<CourseDTO> getAllCourses(int page, int size) {
        return courseRepository.getCourses(PageRequest.of(page, size));
    }
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

    public List<CourseCardDTO> getAllCourseCards() {
        List<CourseCardDTO> courses = courseRepository.getAllCourseCards();

        courses.forEach(course -> {
            // Tính duration theo số tháng giữa startDate và endDate
            long months = ChronoUnit.MONTHS.between(course.getStartDate(), course.getEndDate());
            course.setDuration(months + " tháng");

            // Lấy danh sách giảng viên theo courseId
            List<LecturerDTO> lecturers = getLecturersByCourseId(course.getCourseId());

            // Ghép tên các giảng viên thành chuỗi và gán vào CourseCardDTO
            String lecturerNames = lecturers.stream()
                    .map(LecturerDTO::getUsername) // Sử dụng đúng getter
                    .collect(Collectors.joining(", "));
            course.setAuthor(lecturerNames); // Gán tên giảng viên vào CourseCardDTO
        });

        return courses;
    }
}
