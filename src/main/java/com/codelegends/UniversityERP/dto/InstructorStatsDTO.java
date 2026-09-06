package com.codelegends.UniversityERP.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstructorStatsDTO {
    private Long instructorId;
    private String instructorName;
    private Long totalCoursesTaught;
}
