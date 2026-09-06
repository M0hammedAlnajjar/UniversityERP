package com.codelegends.UniversityERP.dto;

import com.codelegends.UniversityERP.entities.Guardian;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class GuardianDTO {
    private Long id;

    @NotBlank(message = "Guardian name is required")
    @Size(max = 100, message = "Guardian name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Guardian relationship is required")
    @Size(max = 50, message = "Guardian relationship must not exceed 50 characters")
    private String relationship;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Guardian phone number is required")
    @Size(max = 20, message = "Guardian phone number must not exceed 20 characters")
    private String phoneNumber;

    @NotNull(message = "Student ID is required")
    @Positive(message = "Student ID must be positive")
    private Long studentId;

    private String studentName;

    public static GuardianDTO convertToDTO(Guardian entity) {
        if (entity == null) return null;
        return GuardianDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .relationship(entity.getRelationship())
                .studentId(entity.getStudent() == null ? null : entity.getStudent().getId())
                .studentName(entity.getStudent() == null ? null : entity.getStudent().getName())
                .build();
    }

    public static List<GuardianDTO> convertToDTO(List<Guardian> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(GuardianDTO::convertToDTO).toList();
    }
}
