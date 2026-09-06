package com.codelegends.UniversityERP.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramStatsDTO {
    private Long programId;
    private String programName;
    private Long totalEnrolledStudents;
    private Double averageScore;
}
