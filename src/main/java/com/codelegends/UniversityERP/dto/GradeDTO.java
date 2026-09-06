package com.codelegends.UniversityERP.dto;

import com.codelegends.UniversityERP.entities.Grade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeDTO {
    private Long id;

    @NotNull(message = "Score is required")
    @PositiveOrZero(message = "Score must be zero or greater")
    private Double score;

    @NotBlank(message = "Letter grade is required")
    @Size(max = 2, message = "Letter grade must not exceed 2 characters")
    private String letterGrade;

    @NotNull(message = "Enrollment ID is required")
    @Positive(message = "Enrollment ID must be positive")
    private Long enrollmentId;

    @NotNull(message = "Exam ID is required")
    @Positive(message = "Exam ID must be positive")
    private Long examId;

    private String examTitle;

    public static GradeDTO convertToDTO(Grade entity) {
        if (entity == null) return null;
        return GradeDTO.builder()
                .id(entity.getId())
                .score(entity.getScore())
                .letterGrade(entity.getLetterGrade())
                .enrollmentId(entity.getEnrollment() == null ? null : entity.getEnrollment().getId())
                .examId(entity.getExam() == null ? null : entity.getExam().getId())
                .examTitle(entity.getExam() == null ? null : entity.getExam().getTitle())
                .build();
    }

    public static List<GradeDTO> convertToDTO(List<Grade> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(GradeDTO::convertToDTO).toList();
    }
}
