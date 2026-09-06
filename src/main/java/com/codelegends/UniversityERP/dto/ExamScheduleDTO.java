package com.codelegends.UniversityERP.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamScheduleDTO {
    @NotBlank(message = "Exam title is required")
    @Size(max = 100, message = "Exam title must not exceed 100 characters")
    private String title;

    @NotNull(message = "Exam date is required")
    @Future(message = "Exam date must be in the future")
    private LocalDate examDate;

    @NotNull(message = "Total marks are required")
    @Positive(message = "Total marks must be positive")
    private Double totalMarks;
}
