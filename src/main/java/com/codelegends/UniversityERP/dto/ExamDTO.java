package com.codelegends.UniversityERP.dto;

import com.codelegends.UniversityERP.entities.Exam;
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
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamDTO {
    private Long id;

    @NotBlank(message = "Exam title is required")
    @Size(max = 100, message = "Exam title must not exceed 100 characters")
    private String title;

    @NotNull(message = "Exam date is required")
    @Future(message = "Exam date must be in the future")
    private LocalDate examDate;

    @NotNull(message = "Total marks are required")
    @Positive(message = "Total marks must be positive")
    private Double totalMarks;

    @NotNull(message = "Course ID is required")
    @Positive(message = "Course ID must be positive")
    private Long courseId;

    private String courseTitle;

    public static ExamDTO convertToDTO(Exam entity) {
        if (entity == null) return null;
        return ExamDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .examDate(entity.getExamDate())
                .totalMarks(entity.getTotalMarks())
                .courseId(entity.getCourse() == null ? null : entity.getCourse().getId())
                .courseTitle(entity.getCourse() == null ? null : entity.getCourse().getTitle())
                .build();
    }

    public static List<ExamDTO> convertToDTO(List<Exam> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(ExamDTO::convertToDTO).toList();
    }
}
