package com.codelegends.UniversityERP.dto;

import com.codelegends.UniversityERP.entities.Faculty;
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
public class FacultyDTO {

    private Long id;

    @NotBlank(message = "Faculty name is required")
    @Size(max = 100, message = "Faculty name must not exceed 100 characters")
    private String name;

    @Size(max = 255, message = "Faculty description must not exceed 255 characters")
    private String description;

    @NotNull(message = "University ID is required")
    @Positive(message = "University ID must be positive")
    private Long universityId;

    private String universityName;

    public static FacultyDTO convertToDTO(Faculty entity) {
        if (entity == null) {
            return null;
        }
        return FacultyDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .universityId(entity.getUniversity() == null ? null : entity.getUniversity().getId())
                .universityName(entity.getUniversity() == null ? null : entity.getUniversity().getName())
                .build();
    }

    public static List<FacultyDTO> convertToDTO(List<Faculty> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(FacultyDTO::convertToDTO).toList();
    }
}
