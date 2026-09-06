package com.codelegends.UniversityERP.dto;

import com.codelegends.UniversityERP.entities.Department;
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
public class DepartmentDTO {

    private Long id;

    @NotBlank(message = "Department name is required")
    @Size(max = 100, message = "Department name must not exceed 100 characters")
    private String name;

    @Size(max = 255, message = "Department description must not exceed 255 characters")
    private String description;

    @NotNull(message = "Faculty ID is required")
    @Positive(message = "Faculty ID must be positive")
    private Long facultyId;

    private String facultyName;

    public static DepartmentDTO convertToDTO(Department entity) {
        if (entity == null) {
            return null;
        }
        return DepartmentDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .facultyId(entity.getFaculty() == null ? null : entity.getFaculty().getId())
                .facultyName(entity.getFaculty() == null ? null : entity.getFaculty().getName())
                .build();
    }

    public static List<DepartmentDTO> convertToDTO(List<Department> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(DepartmentDTO::convertToDTO).toList();
    }
}
