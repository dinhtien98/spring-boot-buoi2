package com.example.buoi2bt4.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="student_images")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @Column(name = "image_url",length = 300)
    private String imageUrl;
}
