package com.example.buoi2bt4.services;

import com.example.buoi2bt4.models.Student;

import java.util.List;

public interface IStudentService {
    Student getStudentById(Long id);
    List<Student> getAllStudents();
    Student saveStudent(Student student);
    Student updateStudent(Long id,Student student);
    void deleteStudent(Long id);
}
