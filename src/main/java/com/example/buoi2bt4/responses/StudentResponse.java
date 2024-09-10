package com.example.buoi2bt4.responses;


import com.example.buoi2bt4.models.Rating;
import com.example.buoi2bt4.models.Student;
import lombok.*;

import java.time.LocalDate;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponse extends BaseResponse {
    private Long id;
    private String name;
    private String city;
    private LocalDate birthday;
    private Rating rating;
    public static StudentResponse fromStudent(Student student) {
        StudentResponse studentResponse = StudentResponse
                .builder()
                .id(student.getId())
                .name(student.getName())
                .city(student.getCity())
                .birthday(student.getBirthday())
                .rating(student.getRating())
                .build();
        studentResponse.setCreatedAt(student.getCreatedAt());
        studentResponse.setUpdatedAt(student.getUpdatedAt());
        return studentResponse;
    }
}
