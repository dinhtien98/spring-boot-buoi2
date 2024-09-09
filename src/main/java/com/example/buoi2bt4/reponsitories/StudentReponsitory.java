package com.example.buoi2bt4.reponsitories;

import com.example.buoi2bt4.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentReponsitory extends JpaRepository<Student, Long> {
}
