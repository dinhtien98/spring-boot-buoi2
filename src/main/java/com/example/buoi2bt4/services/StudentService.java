package com.example.buoi2bt4.services;

import com.example.buoi2bt4.dto.StudentImageDTO;
import com.example.buoi2bt4.models.Rating;
import com.example.buoi2bt4.models.Student;
import com.example.buoi2bt4.models.StudentImage;
import com.example.buoi2bt4.reponsitories.StudentImageReponsitory;
import com.example.buoi2bt4.reponsitories.StudentReponsitory;
import com.example.buoi2bt4.responses.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService {
    private final StudentReponsitory studentReponsitory;
    private final StudentImageReponsitory studentImage;
    private final StudentImageReponsitory studentImageReponsitory;

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

    @Override
    public List<StudentImage> getStudentImages(Long id) {
        return studentImage.findByStudentId(id);
    }

    @Override
    public StudentImage saveStudentImage(Long id, StudentImageDTO studentImageDTO) {
        Student std = getStudentById(id);
        StudentImage sti = StudentImage
                .builder()
                .student(std)
                .imageUrl(studentImageDTO.getImageUrl())
                .build();
        int size=studentImageReponsitory.findByStudentId(id).size();
        if(size>=4){
            throw new InvalidParameterException("moi sinh vien chi toi da 4 hinh");
        }
        return studentImage.save(sti);
    }
}
