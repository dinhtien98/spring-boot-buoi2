package com.example.buoi2bt4.services;

import com.example.buoi2bt4.models.Rating;
import com.example.buoi2bt4.models.Student;
import com.example.buoi2bt4.reponsitories.StudentReponsitory;
import com.example.buoi2bt4.responses.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        std.setCity(student.getCity());
        std.setBirthday(student.getBirthday());
        std.setRating(student.getRating());
        return studentReponsitory.save(std);
    }

    @Override
    public void deleteStudent(Long id) {
        studentReponsitory.deleteById(id);
    }

    @Override
    public Page<StudentResponse> getStudentReponsitory1(Pageable pageable) {
        return studentReponsitory.findAll(pageable).map(student -> {
            return StudentResponse.fromStudent(student);
        });
    }

    @Override
    public List<Student> findByCityAndName(String name) {
        return studentReponsitory.findByCityAndName(name);
    }

    @Override
    public List<Student> findByBirthdayBetween(int startYear, int endYear) {
        return studentReponsitory.findByBirthdayBetween(startYear, endYear);
    }

    @Override
    public List<Student> searchStudents(Rating rating, String name, String city, int startYear, int endYear) {
        return studentReponsitory.searchStudents(rating, name, city, startYear, endYear);
    }
}
