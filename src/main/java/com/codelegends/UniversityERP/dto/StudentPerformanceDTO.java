package com.codelegends.UniversityERP.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentPerformanceDTO {
    private Long studentId;
    private String studentName;
    private Double averageScore;
    private Double averagePercentage;
    private Double gpa;
    private String classification;
}
