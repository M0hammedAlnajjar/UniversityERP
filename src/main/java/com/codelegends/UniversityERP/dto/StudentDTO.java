package com.codelegends.UniversityERP.dto;

import com.codelegends.UniversityERP.entities.Student;
import com.codelegends.UniversityERP.enums.Gender;
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
public class StudentDTO {

    private Long id;

    @NotBlank(message = "Student name is required")
    @Size(max = 100, message = "Student name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Student gender is required")
    private Gender gender;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Student phone number is required")
    @Size(max = 20, message = "Student phone number must not exceed 20 characters")
    private String phoneNumber;

    @NotBlank(message = "Student major is required")
    @Size(max = 100, message = "Student major must not exceed 100 characters")
    private String major;

    @NotNull(message = "Program ID is required")
    @Positive(message = "Program ID must be positive")
    private Long programId;

    private String programName;

    public static StudentDTO convertToDTO(Student entity) {
        if (entity == null) {
            return null;
        }
        return StudentDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .gender(entity.getGender())
                .major(entity.getMajor())
                .programId(entity.getProgram() == null ? null : entity.getProgram().getId())
                .programName(entity.getProgram() == null ? null : entity.getProgram().getName())
                .build();
    }

    public static List<StudentDTO> convertToDTO(List<Student> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(StudentDTO::convertToDTO).toList();
    }
}
