package com.codelegends.UniversityERP.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniversityStatsDTO {
    private Long universityId;
    private String universityName;
    private Long activeFaculties;
    private Long activeDepartments;
    private Long activeStudents;
}
