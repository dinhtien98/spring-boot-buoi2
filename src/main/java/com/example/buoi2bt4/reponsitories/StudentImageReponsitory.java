package com.example.buoi2bt4.reponsitories;

import com.example.buoi2bt4.models.StudentImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentImageReponsitory extends JpaRepository<StudentImage, Long> {
    List<StudentImage> findByStudentId(long id);
}
