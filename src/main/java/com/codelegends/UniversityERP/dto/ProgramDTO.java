package com.codelegends.UniversityERP.dto;

import com.codelegends.UniversityERP.entities.Program;
import com.codelegends.UniversityERP.enums.DegreeLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class ProgramDTO {

    private Long id;

    @NotBlank(message = "Program name is required")
    @Size(max = 100, message = "Program name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Degree level is required")
    private DegreeLevel degreeLevel;

    @NotNull(message = "Duration years is required")
    @Positive(message = "Duration years must be positive")
    private Integer durationYears;

    @NotNull(message = "Department ID is required")
    @Positive(message = "Department ID must be positive")
    private Long departmentId;

    private String departmentName;

    public static ProgramDTO convertToDTO(Program entity) {
        if (entity == null) {
            return null;
        }
        return ProgramDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .degreeLevel(entity.getDegreeLevel())
                .durationYears(entity.getDurationYears())
                .departmentId(entity.getDepartment() == null ? null : entity.getDepartment().getId())
                .departmentName(entity.getDepartment() == null ? null : entity.getDepartment().getName())
                .build();
    }

    public static List<ProgramDTO> convertToDTO(List<Program> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(ProgramDTO::convertToDTO).toList();
    }
}
