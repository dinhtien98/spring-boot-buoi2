package com.example.buoi2bt4.responses;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StudentListResponse {
    private List<StudentResponse> studentResonseList;
    private int totalPages;
}
