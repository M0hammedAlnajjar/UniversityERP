package com.codelegends.UniversityERP.dto;

import com.codelegends.UniversityERP.entities.Enrollment;
import com.codelegends.UniversityERP.enums.EnrollmentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
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
public class EnrollmentDTO {

    private Long id;

    @NotNull(message = "Enrollment date is required")
    @PastOrPresent(message = "Enrollment date cannot be in the future")
    private LocalDate enrollmentDate;

    @NotNull(message = "Enrollment status is required")
    private EnrollmentStatus status;

    @NotNull(message = "Student ID is required")
    @Positive(message = "Student ID must be positive")
    private Long studentId;

    private String studentName;

    @NotNull(message = "Course ID is required")
    @Positive(message = "Course ID must be positive")
    private Long courseId;

    private String courseTitle;

    public static EnrollmentDTO convertToDTO(Enrollment entity) {
        if (entity == null) {
            return null;
        }
        return EnrollmentDTO.builder()
                .id(entity.getId())
                .enrollmentDate(entity.getEnrollmentDate())
                .status(entity.getStatus())
                .studentId(entity.getStudent() == null ? null : entity.getStudent().getId())
                .studentName(entity.getStudent() == null ? null : entity.getStudent().getName())
                .courseId(entity.getCourse() == null ? null : entity.getCourse().getId())
                .courseTitle(entity.getCourse() == null ? null : entity.getCourse().getTitle())
                .build();
    }

    public static List<EnrollmentDTO> convertToDTO(List<Enrollment> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(EnrollmentDTO::convertToDTO).toList();
    }
}
