package com.codelegends.UniversityERP.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniversitySummaryDTO {
    private Long universities;
    private Long faculties;
    private Long departments;
    private Long programs;
    private Long courses;
    private Long instructors;
    private Long students;
    private Long enrollments;
    private Long exams;
    private Long grades;
    private Long guardians;
    private Long classrooms;
}
