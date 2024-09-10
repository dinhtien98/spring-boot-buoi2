package com.example.buoi2bt4.services;

import com.example.buoi2bt4.models.Student;
import com.example.buoi2bt4.responses.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IStudentService {
    Student getStudentById(Long id);
    List<Student> getAllStudents();
    Student saveStudent(Student student);
    Student updateStudent(Long id,Student student);
    void deleteStudent(Long id);
    Page<StudentResponse> getStudentReponsitory1(Pageable pageable);
    List<Student> findByCityAndName(String name );
}
