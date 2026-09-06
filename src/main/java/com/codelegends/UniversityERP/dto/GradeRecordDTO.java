package com.codelegends.UniversityERP.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeRecordDTO {
    @NotNull(message = "Score is required")
    @PositiveOrZero(message = "Score must be zero or greater")
    private Double score;

    @NotBlank(message = "Letter grade is required")
    @Size(max = 2, message = "Letter grade must not exceed 2 characters")
    private String letterGrade;
}
