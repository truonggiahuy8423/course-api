package com.example.course.service;

import com.example.course.dto.response.GetLecturerPageDTO;
import com.example.course.dto.response.LecturerPageDTO;
import com.example.course.entity.Lecturer;
import com.example.course.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LecturerService {
    @Autowired
    private LecturerRepository lecturerRepository;

    public GetLecturerPageDTO getAllLecturers(Integer page, Integer pageSize, String sort, String sortDir) {
        String sortAttr = getSortAttributeLecturers(sort); // Hàm lấy thuộc tính sắp xếp tương ứng từ số
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, sortAttr);

        List<LecturerPageDTO> lstLecturerPageDTO = lecturerRepository.getAllLecturerInfo(pageable);
        Integer total = lstLecturerPageDTO.size();
        return new GetLecturerPageDTO(lstLecturerPageDTO, lstLecturerPageDTO.size());
    }

    private String getSortAttributeLecturers(String sort) {
        Map<Integer, String> sortMapper = new HashMap<>();
        sortMapper.put(1, "l.lecturerId");
        sortMapper.put(2, "u.username");
        return sortMapper.get(Integer.valueOf(sort));
    }
}
