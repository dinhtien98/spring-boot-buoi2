package com.example.buoi2bt4.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentImageDTO {

    @JsonProperty("student_id")
    private int studentId;
    @Size(min=5,max = 200, message = "ten cua hinh anh tu 5 den 200")
    @JsonProperty("image_url")
    private String imageUrl;
}
