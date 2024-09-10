package com.example.buoi2bt4.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Table(name="students")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Student extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "tên không được để trống")
    @Size(min=2,max = 50, message = "tên phải có từ 2 đến 50 ký tự")
    private String name;

    @NotBlank(message = "thành phố không được để trống")
    private String city;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    @Past(message = "phải là 1 ngày trong quá khứ")
    private LocalDate birthday;

    @NotNull(message = "xếp loại không được để trống")
    @Enumerated(EnumType.STRING)
    private Rating rating;
}
