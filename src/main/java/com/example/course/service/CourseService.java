package com.example.course.service;

import com.example.course.dto.request.CreateCourseRequest;
import com.example.course.dto.response.*;
import com.example.course.entity.*;
import com.example.course.entity.composite.CourseLecturerId;
import com.example.course.entity.composite.CourseStudentId;
import com.example.course.exception.AppRuntimeException;
import com.example.course.repository.*;
import com.example.course.util.constant.ExceptionType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

//import static java.util.stream.Nodes.collect;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LecturerRepository lecturerRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private CourseStudentRepository courseStudentRepository;
    @Autowired
    private CourseLecturerRepository courseLecturerRepository;
    @Autowired
    private CourseScheduleRepository courseScheduleRepository;

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

    public GetSubjectsDTO getSubjects(Integer page, Integer pageSize, String sort, String sortDir) {
        String sortAttr = getSortAttributeSubjects(sort); // Hàm lấy thuộc tính sắp xếp tương ứng từ số
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, sortAttr);

        List<SubjectInCreateCourseDTO> subjects = subjectRepository.findSubjects(pageable);
        Integer total = subjectRepository.findAll().size();
        return new GetSubjectsDTO(subjects, total);
    }

    public GetStudentsDTO getStudents(Integer page, Integer pageSize, String sort, String sortDir) {
        String sortAttr = getSortAttributeStudents(sort); // Hàm lấy thuộc tính sắp xếp tương ứng từ số
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, sortAttr);

        List<StudentInCreateCourseDTO> students = studentRepository.findStudents(pageable);
        Integer total = studentRepository.findAll().size();
        return new GetStudentsDTO(students, total);
    }

    public GetRoomsDTO getRooms(Integer page, Integer pageSize, String sort, String sortDir) {
        String sortAttr = getSortAttributeRooms(sort); // Hàm lấy thuộc tính sắp xếp tương ứng từ số
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, sortAttr);

        List<RoomDTO> rooms = roomRepository.findRooms(pageable);
        Integer total = roomRepository.findAll().size();
        return new GetRoomsDTO(rooms, total);
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

    private String getSortAttributeStudents(String sort) {
        Map<Integer, String> sortMapper = new HashMap<>();
        sortMapper.put(1, "s.studentId");
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

    private String getSortAttributeRooms(String sort) {
        Map<Integer, String> sortMapper = new HashMap<>();
        sortMapper.put(1, "r.roomId");
        sortMapper.put(2, "r.roomName");
//        sortMapper.put(3, "c.startDate");
//        sortMapper.put(4, "c.endDate");
//        sortMapper.put(5, "COUNT(s)");

        return sortMapper.get(Integer.valueOf(sort));
    }

    @Transactional
    public CreateCourseResponse createCourse(CreateCourseRequest courseRequest) {
        // Authorization
        // Business
        Long subjectId = courseRequest.getSubjectId();
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new AppRuntimeException(ExceptionType.DATA_INTEGRITY_VIOLATION, "Subject(ID: " + subjectId + ") does not exist"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // hoặc định dạng bạn muốn

        Course course = Course.builder()
                .startDate(LocalDate.parse(courseRequest.getStartDate(), formatter))
                        .endDate(LocalDate.parse(courseRequest.getEndDate(), formatter))
                        .subject(subject)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        course = courseRepository.save(course);

        addStudentToCourse(course.getCourseId(), courseRequest.getStudentIds());

        addLecturerToCourse(course.getCourseId(), courseRequest.getLecturerIds());

        DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"); // hoặc định dạng bạn muốn

        Course finalCourse = course;
        List<CourseSchedule> courseScheduleList = courseRequest.getSchedules()
                .stream().map((scheduleRequestDTO -> {
                    return CourseSchedule.builder()
                            .course(finalCourse)  // Truyền đối tượng course vào CourseSchedule
                            .startTime(LocalDateTime.parse(scheduleRequestDTO.getStartTime(), formatterDateTime))  // Giả sử scheduleRequestDTO có startTime
                            .endTime(LocalDateTime.parse(scheduleRequestDTO.getEndTime(), formatterDateTime))  // Giả sử scheduleRequestDTO có endTime
                            .createdDate(LocalDateTime.now())
                            .updatedDate(LocalDateTime.now())
                            .build();
                })).toList();
        courseScheduleRepository.saveAll(courseScheduleList);

        System.out.println("Courses in subject object: " + subject.getCourses().size());


        return null;
    }

    public void addStudentToCourse(Long courseId, List<Long> studentIds) {
        System.out.println("Course: " + courseId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new AppRuntimeException(ExceptionType.DATA_INTEGRITY_VIOLATION, "Course(ID: " + courseId + ") does not exist"));

        List<Student> students = studentRepository.findAllById(studentIds);

        Set<Long> foundStudentIds = students.stream()
                .map(Student::getStudentId)
                .collect(Collectors.toSet());

        List<Long> nonExistentIds = studentIds.stream()
                .filter(id -> !foundStudentIds.contains(id))
                .toList();

        if (!nonExistentIds.isEmpty()) {
            throw new AppRuntimeException(ExceptionType.DATA_INTEGRITY_VIOLATION, "Students(ID: " + nonExistentIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ")) + ") do not exist");
        }

        // Kiểm tra trùng lặp trước khi thêm vào courseStudentRepository
        for (Student student : students) {
            CourseStudentId courseStudentId = new CourseStudentId(courseId, student.getStudentId());
            Optional<CourseStudent> existingCourseStudent = courseStudentRepository.findById(courseStudentId);
            if (existingCourseStudent.isEmpty()) {
                CourseStudent courseStudent = CourseStudent.builder()
                        .id(courseStudentId)
                        .course(course)
                        .student(student)
                        .build();
                courseStudentRepository.save(courseStudent);
            }
        }
    }

    @Transactional
    public void addLecturerToCourse(Long courseId, List<Long> lecturerIds) {
        // Tìm khóa học từ courseId
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new AppRuntimeException(ExceptionType.DATA_INTEGRITY_VIOLATION, "Course(ID: " + courseId + ") does not exist"));

        // Tìm các giảng viên theo lecturerIds
        List<Lecturer> lecturers = lecturerRepository.findAllById(lecturerIds);

        // Lấy danh sách các id giảng viên đã tìm thấy
        Set<Long> foundLecturerIds = lecturers.stream()
                .map(Lecturer::getLecturerId) // Giả sử Lecturer có phương thức getLecturerId()
                .collect(Collectors.toSet());

        // Kiểm tra các giảng viên không tồn tại trong database
        List<Long> nonExistentIds = lecturerIds.stream()
                .filter(id -> !foundLecturerIds.contains(id))
                .toList();

        // Nếu có giảng viên không tồn tại, ném ngoại lệ
        if (!nonExistentIds.isEmpty()) {
            throw new AppRuntimeException(ExceptionType.DATA_INTEGRITY_VIOLATION, "Lecturers(ID: " + nonExistentIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ")) + ") do not exist");
        }

        // Lặp qua các giảng viên đã tìm thấy và kiểm tra xem có tồn tại mối quan hệ CourseLecturer chưa
        for (Lecturer lecturer : lecturers) {
            CourseLecturerId courseLecturerId = new CourseLecturerId(courseId, lecturer.getLecturerId()); // Tạo id khóa học - giảng viên
            Optional<CourseLecturer> existingCourseLecturer = courseLecturerRepository.findById(courseLecturerId);

            if (existingCourseLecturer.isEmpty()) {
                // Nếu chưa có mối quan hệ, tạo mới và lưu vào database
                CourseLecturer courseLecturer = CourseLecturer.builder()
                        .id(courseLecturerId)
                        .course(course)
                        .lecturer(lecturer)
                        .build();
                courseLecturerRepository.save(courseLecturer);
            }
        }
    }



    public List<LecturerDTO> getLecturersByCourseId(Long courseId) {
        return lecturerRepository.findByCourseId(courseId);
    }

    public List<Object[]> test(Integer page_index, Integer limit) {
        Pageable pageable = PageRequest.of(page_index - 1, limit); // Trang đầu tiên, 5 kết quả
        return courseRepository.test(pageable);
    }
}