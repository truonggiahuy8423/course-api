package com.example.course.service.Lecturer;

import com.example.course.dto.response.*;
import com.example.course.entity.Lecturer;
import com.example.course.entity.User;
import com.example.course.repository.LecturerRepository;
import com.example.course.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LectureService implements ILectureService {
    private final LecturerRepository lecturerRepository;
    private final UserRepository userRepository;

    @Transactional
    public AppResponse<LecturerUserDTO> addLecturer(Lecturer lecturerPayload) {
        // Lưu Lecturer vào cơ sở dữ liệu trước để lấy lecturerId
        Lecturer lecturer = new Lecturer();
        Lecturer savedLecturer = lecturerRepository.save(lecturer);

        // Tạo thực thể User mới từ thông tin payload
        User user = new User();
        user.setLecturer(savedLecturer);
        user.setUsername(lecturerPayload.getUser().getUsername());
        user.setEmail(lecturerPayload.getUser().getEmail());
        user.setGender(lecturerPayload.getUser().getGender());
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());

        // Kiểm tra trùng lặp email
        if (userRepository.existsByEmail(user.getEmail())) {
            return new AppResponse<>(400, "Email already exists", null);
        }

        // Lưu User vào cơ sở dữ liệu
        userRepository.save(user);

        // Tạo DTO để trả về
        LecturerUserDTO lecturerDTO = new LecturerUserDTO(
                savedLecturer.getLecturerId(),
                user.getUsername(),
                user.getEmail(),
                user.getGender(),
                user.getCreatedDate(),
                user.getUpdatedDate()
        );

        return new AppResponse<>(201, "Lecturer added successfully", lecturerDTO);
    }





    public List<LecturerDTO> getAllLecturers(int page, int size) {
        return lecturerRepository.getLecturers(PageRequest.of(page, size));
    }



    public GetLecturerDTO getAllLecture(Integer page, Integer pageSize, String sort, String sortDir) {
        String sortAttr = getSortAttribute(sort);
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, sortAttr);

        // Lấy danh sách giảng viên theo phân trang
        List<LecturerDTO> lecturers = lecturerRepository.getLecturers(pageable);
        return new GetLecturerDTO(lecturers, (int) lecturerRepository.count());
    }

    private String getSortAttribute(String sort) {
        Map<Integer, String> sortMapper = new HashMap<>();
        sortMapper.put(1, "l.lecturerId");
        sortMapper.put(2, "u.username");
        sortMapper.put(3, "u.email");
        sortMapper.put(4, "u.lastAccess");
        sortMapper.put(5, "u.gender");

        return sortMapper.getOrDefault(Integer.valueOf(sort), "l.lecturerId"); // Mặc định là "lecturerId" nếu giá trị không hợp lệ
    }

    @Override
    public AppResponse<Lecturer> updateLecture(Lecturer lecture) {
        return null; // Implement this method if needed
    }

    @Override
    public AppResponse<Lecturer> deleteLecture(Lecturer lecturer) {
        AppResponse<Lecturer> response = new AppResponse<>();
        try {
            if (lecturerRepository.existsById(lecturer.getLecturerId())) {
                userRepository.deleteByLecturerId(lecturer.getLecturerId());
                lecturerRepository.deleteById(lecturer.getLecturerId());
                response.setMessage("Lecturer deleted successfully");
                response.setData(lecturer);
            } else {
                response.setMessage("Lecturer not found");
            }
        } catch (Exception e) {
            response.setMessage("An error occurred while deleting lecturer: " + e.getMessage());
        }
        return response;
    }


}
