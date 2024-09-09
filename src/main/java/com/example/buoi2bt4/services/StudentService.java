package com.example.buoi2bt4.services;

import com.example.buoi2bt4.models.Student;
import com.example.buoi2bt4.reponsitories.StudentReponsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService {
    private final StudentReponsitory studentReponsitory;

    @Override
    public Student getStudentById(Long id) {
        return studentReponsitory.findById(id).orElse(null);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentReponsitory.findAll();
    }

    @Override
    public Student saveStudent(Student student) {
        Student std = Student
                .builder()
                .name(student.getName())
                .city(student.getCity())
                .birthday(student.getBirthday())
                .rating(student.getRating())
                .build();
        return studentReponsitory.save(std);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        Student std =getStudentById(id);
        std.setName(student.getName());
        return studentReponsitory.save(std);
    }

    @Override
    public void deleteStudent(Long id) {
        studentReponsitory.deleteById(id);
    }
}
