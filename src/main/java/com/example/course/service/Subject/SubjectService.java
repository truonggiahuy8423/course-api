package com.example.course.service.Subject;

import com.example.course.dto.response.AppResponse;
import com.example.course.dto.response.ErrorResponse;
import com.example.course.entity.Category;
import com.example.course.entity.Subject;
import com.example.course.repository.CategoryRepository;
import com.example.course.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class  SubjectService implements ISubjectService {
    private final SubjectRepository subjectRepository;

    private final CategoryRepository categoryRepository;
    @Override
    public AppResponse<Subject> addSubject(Subject subject) {

        if (subject.getCategory() != null && subject.getCategory().getCategoryId() != null) {
            Optional<Category> category = categoryRepository.findById(subject.getCategory().getCategoryId());
            if (category.isPresent()) {
                subject.setCategory(category.get());
            } else {
                // Return an error response if the category is not found
                ErrorResponse error = new ErrorResponse(400, "Category with given ID not found");
                return new AppResponse<>(400, "Failed to add subject", error);
            }
        }
        Subject savedSubject = subjectRepository.save(subject);
        return new AppResponse<>(201, "Subject added successfully", savedSubject);
    }

    @Override
    public AppResponse<Subject> updateSubject(Subject subject) {
        return null;
    }

    @Override
    public AppResponse<Subject> deleteSubject(Subject subject) {
        return null;
    }

    @Override
    public AppResponse<List<Subject>> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return new AppResponse<>(200,"Get list subject successfully", subjects);
    }
}
