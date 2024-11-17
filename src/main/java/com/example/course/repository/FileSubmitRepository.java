package com.example.course.repository;

import com.example.course.entity.FileSubmit;
import com.example.course.entity.composite.FileSubmitId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileSubmitRepository extends JpaRepository<FileSubmit, FileSubmitId> {
}
