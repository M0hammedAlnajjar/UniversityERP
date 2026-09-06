package com.codelegends.UniversityERP.dto;

import com.codelegends.UniversityERP.entities.Instructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
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
public class InstructorDTO {

    private Long id;

    @NotBlank(message = "Instructor name is required")
    @Size(max = 100, message = "Instructor name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Instructor email is required")
    @Email(message = "Instructor email must be valid")
    @Size(max = 150, message = "Instructor email must not exceed 150 characters")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Instructor phone number is required")
    @Size(max = 20, message = "Instructor phone number must not exceed 20 characters")
    private String phoneNumber;

    @NotBlank(message = "Instructor specialization is required")
    @Size(max = 100, message = "Instructor specialization must not exceed 100 characters")
    private String specialization;

    @NotNull(message = "Department ID is required")
    @Positive(message = "Department ID must be positive")
    private Long departmentId;

    private String departmentName;

    public static InstructorDTO convertToDTO(Instructor entity) {
        if (entity == null) {
            return null;
        }
        return InstructorDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .specialization(entity.getSpecialization())
                .departmentId(entity.getDepartment() == null ? null : entity.getDepartment().getId())
                .departmentName(entity.getDepartment() == null ? null : entity.getDepartment().getName())
                .build();
    }

    public static List<InstructorDTO> convertToDTO(List<Instructor> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(InstructorDTO::convertToDTO).toList();
    }
}
