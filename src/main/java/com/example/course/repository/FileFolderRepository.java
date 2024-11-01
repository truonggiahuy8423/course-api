package com.example.course.repository;

import com.example.course.entity.FileFolder;
import com.example.course.entity.composite.FileFolderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileFolderRepository extends JpaRepository<FileFolder, FileFolderId> {
}
